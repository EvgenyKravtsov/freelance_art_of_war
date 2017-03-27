package com.tesseractumstudios.warhammer_artofwar.Screens.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import art.of.war.tesseractumstudios.R;
import com.tesseractumstudios.warhammer_artofwar.util.IabBroadcastReceiver;
import com.tesseractumstudios.warhammer_artofwar.util.IabHelper;
import com.tesseractumstudios.warhammer_artofwar.util.IabResult;
import com.tesseractumstudios.warhammer_artofwar.util.Inventory;
import com.tesseractumstudios.warhammer_artofwar.util.Purchase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainMenu extends ActionBarActivity implements IabBroadcastReceiver.IabBroadcastListener {
    private static final String TAG                     = "MainMenu";
    private static final String PREFERENCES_FILENAME    = "WarhammerArtOfWar";
    private static final String IS_INSTALLED_KEY        = "isInstalled";
    private static final int    BUTTONS_MARGIN          = 48;
    private static final int    WIDE_SCREEN_MARGIN      = 500;

    private ImageView   backGround;
    private ImageView searchFieldPlaceholderImage;
    private LinearLayout randomizerButton, rulesButton, fractionRulesButton,
                                armoryButton, chartsAndTablesButton;
    private EditText    searchField;

    private IabHelper                                   iabHelper;
    private IabBroadcastReceiver                        iabBroadcastReceiver;
    private IabHelper.QueryInventoryFinishedListener    gotInventoryListener;
    private IabHelper.OnIabPurchaseFinishedListener     purchaseFinishedListener;

    private boolean gotRandomizer = false;
    String deviceId;

    ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);



        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        deviceId= telephonyManager.getDeviceId();

        DisplayMetrics      metrics         = getResources().getDisplayMetrics();
        float               screenDpWidth   = metrics.widthPixels / metrics.density;
        SharedPreferences   preferences     = this.getSharedPreferences(
                                                    PREFERENCES_FILENAME, MODE_PRIVATE);
    //    String              base64Data      = getString(R.string._webData1) + getString(R.string._webData2) + getString(R.string._webData3) + getString(R.string._webData4);

        String              base64Data      = getString(R.string.key_app);

        findViews();

        setListeners();

        if ( !preferences.getBoolean(IS_INSTALLED_KEY, false) ) {
            copyAssetFolder(getAssets(), "app_data",
                            getFilesDir().getAbsolutePath() + "/app_data");
            //TODO delete before release
            preferences.edit().putBoolean(IS_INSTALLED_KEY, true).apply();
        }

        iabHelper = new IabHelper(this, base64Data);
        gotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            @Override
            public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                if (iabHelper == null) return;

                if (result.isFailure()) {
                    Log.e(TAG, "Failed to query inventory: " + result);
                    return;
                }

                Purchase randomizer = inv.getPurchase("randomizer_access");

                gotRandomizer = randomizer != null; //TODO add verifyDeveloperPayload(Purchase p)

                Log.e(TAG, "Randomizer is: " + gotRandomizer);
            }
        };
        purchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
            @Override
            public void onIabPurchaseFinished(IabResult result, Purchase info) {
                if (iabHelper == null) return;

                if (result.isFailure()) {
                    Log.e(TAG, "Error purchasing: " + result);
                    return;
                }

                if ( info.getSku().equals("randomizer_access") ) {

                    Log.e(TAG, "Purchased: " +info.getSku());

                    gotRandomizer = true;
                }
            }
        };
        // TODO BILLING
//        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
//            public void onIabSetupFinished(IabResult result) {
//                if (!result.isSuccess()) {
//                    Log.e(TAG, "Problem setting up In-app Billing: " + result);
//                }
//                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
//
//                iabBroadcastReceiver = new IabBroadcastReceiver(MainMenu.this);
//                registerReceiver(iabBroadcastReceiver, broadcastFilter);
//
//                Log.d(TAG, "Setup successful. Querying inventory.");
//                try {
//                    iabHelper.queryInventoryAsync(gotInventoryListener);
//                } catch (IabHelper.IabAsyncInProgressException e) {
//                    Log.e(TAG, "Error querying inventory. Another async operation in progress.");
//                }
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchField.clearFocus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if ( iabHelper != null ) {
            try {
                Log.d(TAG, "Inapp call dispose");
                iabHelper.dispose();
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        }
        iabHelper = null;

        unregisterReceiver(iabBroadcastReceiver);
    }

    @Override
    public void receivedBroadcast() {
        Log.d(TAG, "Received broadcast notification. Querying inventory.");
        try {
            iabHelper.queryInventoryAsync(gotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            Log.d(TAG, "Error querying inventory. Another async operation in progress.");
        }
    }

    ////

    private void findAllFiles() {
        Intent intent = new Intent(MainMenu.this, SearchResultActivity.class);

        intent.putExtra(SearchResultActivity.getQueryTag(), searchField.getText().toString());
        startActivity(intent);
    }

    private boolean copyAssetFolder(AssetManager assetManager, String fromAssetPath,
                                            String toPath) {
        try {
            String[]    files   = assetManager.list(fromAssetPath);
            boolean     res     = true;

            new File(toPath).mkdirs();
            for (String file : files) {
                if (file.contains(".")) {
                    res &= copyAsset(assetManager, fromAssetPath + "/" + file,
                                            toPath + "/" + file);
                } else {
                    res &= copyAssetFolder(assetManager, fromAssetPath + "/" + file,
                                                toPath + "/" + file);
                }
            }

            return res;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }
    private static boolean copyAsset(AssetManager assetManager,
                                     String fromAssetPath, String toPath) {
        InputStream     in  = null;
        OutputStream    out = null;
        try {
            in  = assetManager.open(fromAssetPath);
            out = new FileOutputStream(toPath);

            new File(toPath).createNewFile();
            copyFile(in, out);

            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[]  buffer = new byte[1024];
        int     read;

        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    private void setListeners() {
        randomizerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(deviceId.equals("866147023516315") || deviceId.equals("86417902126425")){
                    Intent intent = new Intent(MainMenu.this, RandomizerMenuActivity.class);

                    startActivity(intent);
                }

                 else  if ( gotRandomizer ) {
                    Log.e("got_randamizer","got randamizer");

                    Intent intent = new Intent(MainMenu.this, RandomizerMenuActivity.class);

                    startActivity(intent);
                } else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                    builder.setMessage(getResources().getString(R.string.subscribe_message))
                            .setCancelable(false)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("Subscribe", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub

                                    try {
                                        iabHelper.launchPurchaseFlow(MainMenu.this, "randomizer_access", 101, purchaseFinishedListener, "");
                                    } catch (IabHelper.IabAsyncInProgressException e) {
                                        e.printStackTrace();
                                        Log.e(TAG, "Error launching purchase flow. Another async operation in progress.");
                                    }
                                    dialog.dismiss();

                                }
                            });

                    final AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });

        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, RulesActivity.class);

                intent.putExtra("path", "app_data/rules");
                startActivity(intent);
            }
        });

        fractionRulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, FractionRulesActivity.class);

                startActivity(intent);
            }
        });

        armoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Armory.class);

                startActivity(intent);
            }
        });

        chartsAndTablesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, ChartsAndTablesActivity.class);
                intent.putExtra("path", "app_data/charts_and_tables");
                startActivity(intent);
            }
        });

        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                findAllFiles();
                return true;
            }
        });

        searchField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchFieldPlaceholderImage.setVisibility(View.GONE);
                }
                else {
                    if (searchField.getText().toString().length() == 0)
                        searchFieldPlaceholderImage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void findViews() {
        randomizerButton        = (LinearLayout) findViewById(R.id.main_menu_randomizer_button);
        rulesButton             = (LinearLayout) findViewById(R.id.main_menu_rules_button);
        fractionRulesButton     = (LinearLayout) findViewById(R.id.main_menu_fraction_rules_button);
        armoryButton            = (LinearLayout) findViewById(R.id.main_menu_armory_button);
        chartsAndTablesButton   = (LinearLayout) findViewById(R.id.main_menu_chart_tables_button);
        searchField             = (EditText) findViewById(R.id.main_menu_search_field);
        backGround              = (ImageView) findViewById(R.id.bg);

        searchFieldPlaceholderImage = (ImageView)
                findViewById(R.id.mainActivity_searchFieldPlaceholderImage);
    }

    public static void refreshActivity(Activity ct){
        Intent it = new Intent(ct,MainMenu.class);
       ct.finishAffinity();
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       ct. startActivity(it);

    }
}
