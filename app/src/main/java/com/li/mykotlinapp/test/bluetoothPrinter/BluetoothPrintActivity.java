package com.li.mykotlinapp.test.bluetoothPrinter;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apkfuns.logutils.LogUtils;
import com.li.mykotlinapp.R;
import com.li.mykotlinapp.adapter.TestButtonListAdapter;
import com.li.mykotlinapp.base.BaseActivity;
import com.li.mykotlinapp.util.ClsUtils;
import com.li.mykotlinapp.util.RxUtil;
import com.snbc.sdk.barcode.BarInstructionImpl.BarPrinter;
import com.snbc.sdk.barcode.IBarInstruction.ILabelEdit;
import com.snbc.sdk.barcode.IBarInstruction.ILabelQuery;
import com.snbc.sdk.barcode.enumeration.Area;
import com.snbc.sdk.barcode.enumeration.BarCodeType;
import com.snbc.sdk.barcode.enumeration.HRIPosition;
import com.snbc.sdk.barcode.enumeration.InstructionType;
import com.snbc.sdk.barcode.enumeration.PrinterDirection;
import com.snbc.sdk.barcode.enumeration.QRLevel;
import com.snbc.sdk.barcode.enumeration.QRMode;
import com.snbc.sdk.barcode.enumeration.Rotation;
import com.snbc.sdk.connect.connectImpl.BluetoothConnect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test.bluetoothPrinter
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/3/10
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
public class BluetoothPrintActivity extends BaseActivity {
    public static int port_type = 0;
    //    public POSSDK pos_blue = null;
//    private POSInterfaceAPI interface_blue = null;
    private static final int PRINT_MODE_STANDARD = 0;
    private static final int PRINT_MODE_PAGE = 1;

    private String cur_address = null;
    public static int printMode = PRINT_MODE_STANDARD;
    private int hd_type = 0;

    private TestButtonListAdapter adapter;
    private List<String> btnList;
    private RecyclerView rcv_button;
    //    private TestPrintInfo testprint;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    //    private POSBluetoothAPI mBluetoothManager;
    private int fontStyle = 0;
    private int fontType = 0;
    private int dataLength = 0;
    private int alignment = 0;
    private int horStartingPosition = 0;
    private int verStartingPosition = 0;
    private int lineHeight = 0;
    private int horizontalTimes = 0;
    private int verticalTimes = 0;
    public static final int POS_SUCCESS = 1000;
    private BarPrinter printer;
    private BluetoothConnect bluetoothConnect;
    private TextView tvInfo;

    @Override
    public int getLayout() {
        return R.layout.activity_bluetooth_printer;
    }

    @Override
    public void initData() {
//        mBluetoothManager = POSBluetoothAPI.getInstance(this);
        checkConnect();
        rcv_button = findViewById(R.id.rcv_button);
        tvInfo = findViewById(R.id.tv_info);
        tvInfo.setOnLongClickListener(view -> {
            tvInfo.setText("");
            return true;
        });
        btnList = new ArrayList<>();
        btnList.add("connect");
        btnList.add("checkStatus");
        btnList.add("Paired Devices");
        btnList.add("Search Devices");
        btnList.add("Cancel search Devices");
        btnList.add("Disconnect Devices");
        btnList.add("Stand Mode");
        btnList.add("Page Mode");
        btnList.add("Print Bitmap");
        btnList.add("Print Text");
        btnList.add("Print BarCode");
        btnList.add("Print QR");
        btnList.add("Cut Paper");
        btnList.add("Feed Paper");

        adapter = new TestButtonListAdapter(btnList);
        rcv_button.setAdapter(adapter);
        rcv_button.setLayoutManager(new LinearLayoutManager(mContext));
        rcv_button.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        adapter.addData(btnList);
        initEvent();
//        testprint = new TestPrintInfo();
        BluetoothConnectActivityReceiver mFindBlueToothReceiver = new BluetoothConnectActivityReceiver();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mFindBlueToothReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mFindBlueToothReceiver, filter);
//        interface_blue = POSBluetoothAPI.getInstance(this);
    }

    private void checkSelfPermission() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
    }

    private void cancelDiscovery() {
        checkSelfPermission();
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    private void printText() {
//        int connectState = mBluetoothManager.getConnectState();
//        LogUtils.e("connectState:" + connectState);
//
//        String spi_str;
//        fontStyle = 0;
//        fontStyle |= 0x400;
//        //Get PrintData
//        String text_data = "1234567890abcdefghijklmnopqrstuvwxyz";
//
//        //Get FontType
//        fontType = 0;
//
//        //Get Alignmenttype
//        alignment = 0;
//
//        //Get HorStartingPosition
//        spi_str = "0";
//        horStartingPosition = 100;
//
//        //Get VerStartingPosition
//        spi_str = "1";
//        verStartingPosition = 20;
//
//        //Get LineHeight
//        spi_str = "10";
//        lineHeight = 10;
//
//        //Get HorizontalTimes
//        spi_str = "0";
//        horizontalTimes = 1;
//
//        //Get VerticalTimes
//        verticalTimes = 1;
//
//        //Get data length
//        try {
//            dataLength = text_data.getBytes("GB18030").length;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        //Bluetooth
//        int error_code = testprint.TestPrintText(pos_blue, PRINT_MODE_STANDARD, text_data, dataLength, fontType, fontStyle,
//                alignment, horStartingPosition, verStartingPosition, lineHeight, horizontalTimes, verticalTimes);
//        if (error_code != POS_SUCCESS) {
//            shortToast("error:" + error_code);
//        }
//        LogUtils.e("error_code:" + error_code);
    }

    /**
     * 状态查询
     */
    private boolean checkStatus() {
        ILabelQuery labelQuery = printer.labelQuery();
        byte[] status = new byte[]{0x00, 0x00};
        int nReturn = 0;
        try {
            nReturn = labelQuery.getPrinterStatus(status);
        } catch (Exception e) {
            e.printStackTrace();
            nReturn = -1;
            tvInfo.setText("异常：" + e.getMessage());
            return false;
        }
        LogUtils.e("nReturn:" + nReturn);
        String string = " 正常";
//        if (nReturn == 0) {
//            tvInfo.setText(string);
//            return true;
//        }
        if (nReturn < 0) {
            string = " 读取失败  ";
        }
        if ((status[0] & 0x01) == 0x01) {
            string = " 缺纸  ";
        }
        if ((status[0] & 0x02) == 0x02) {
            string = " 上盖抬起 ";
        }
        //zpl指令集状态
        if ((status[0] & 0x04) == 0x04) {
            string = " 打印机忙 ";
        }

        if ((status[0] & 0x08) == 0x08) {
            string = " 打印头过热 ";
        }
        if ((status[0] & 0x10) == 0x10) {
            string = " 暂停 ";
        }

        if ((status[0] & 0x20) == 0x20) {
            string = " 碳带用尽 ";
        }
        tvInfo.setText(string);
        return false;
    }

    private void checkConnect() {
        int status = BluetoothAdapter.getDefaultAdapter().getState();
        LogUtils.e("getState:" + status);
    }

    private void connect() {
        showLoading("连接中，请稍后。");
        bluetoothConnect =
                new BluetoothConnect(BluetoothAdapter.getDefaultAdapter(), "04:7F:0E:56:58:FA");
        bluetoothConnect.DecodeType("GB18030");
        boolean isConnect = true;
        try {
            bluetoothConnect.connect();
            BarPrinter.BarPrinterBuilder builder = new BarPrinter.BarPrinterBuilder();
            builder.buildDeviceConnenct(bluetoothConnect);
            builder.buildInstruction(InstructionType.valueOf("BPLC"));
            printer = builder.getBarPrinter();
        } catch (Exception e) {
            hideLoading();
            LogUtils.e("连接打印机失败：" + e.getMessage());
            tvInfo.setText("连接打印机失败：" + e.getMessage());
            isConnect = false;
        }

        if (isConnect) {
            Observable.timer(1, TimeUnit.SECONDS)
                    .compose(RxUtil.INSTANCE.trans_io_main())
                    .subscribe(aLong -> {
                        if (checkStatus()){
                            tvInfo.setText("连接打印机成功");
                        }else {
                            tvInfo.append("连接打印机失败");
                        }
                        hideLoading();
                    });
        }
    }

    private void initEvent() {
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            String bean = adapter.getItem(position);
            switch (bean) {
                case "checkStatus": {
                    checkStatus();
                    break;
                }
                case "connect": {
                    connect();
                    break;
                }
                case "Set Visibility": {
                    break;

                }
                case "Paired Devices": {
                    break;

                }
                case "Search Devices": {
                    checkSelfPermission();
                    mBluetoothAdapter.startDiscovery();
                    break;
                }
                case "Cancel search Devices": {
                    cancelDiscovery();
                    break;
                }
                case "Disconnect Devices": {
                    boolean b = true;

                    try {
                        bluetoothConnect.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                        b= false;
                    }

                    if (b) {
                        Observable.timer(1, TimeUnit.SECONDS)
                                .compose(RxUtil.INSTANCE.trans_io_main())
                                .subscribe(aLong -> {
                                    if (checkStatus()){
                                        tvInfo.setText("未断开");
                                    }else {
                                        tvInfo.append("已断开");
                                    }
                                });
                    }
                    break;
                }
                case "Stand Mode": {
                    break;
                }
                case "Page Mode": {
                    break;
                }
                case "Print Bitmap": {
                    printImg();
//                    printBitmap();
                    break;

                }
                case "Print Text": {
                    printLabel();
                    break;
                }
                case "Print BarCode": {
                    break;

                }
                case "Print QR": {
                    break;

                }
                case "Cut Paper": {
//                    int result = testprint.TestCutPaper(pos_blue, printMode);
//                    LogUtils.e("TestCutPaper result" + result);
                    break;
                }
                case "Feed Paper": {
                    try {
                        printer.labelControl().feedLabel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    int error_code = testprint.TestFeedLine(pos_blue, PRINT_MODE_PAGE);
//                    if (error_code != POS_SUCCESS) {
//                        cur_address = null;
//                        shortToast("Failed to Feed Paper.");
//                    }
                    break;

                }
            }
        });
    }

    /**
     * void printText(int x, int y, String fontName, String content,Rotation angle, int horiSize,int vertSize,int bold,int Reverse)
     * x：x坐标，单位：点
     * y：y坐标，单位：点
     * fontName：字体名称，与vertSize组和使用，详见下表：
     * content：字体内容
     * angle：旋转角度
     * horiSize：无意义
     * bold：加粗，1：加粗，0，不加粗
     * Reverse：反显，1：反显，0，不反显
     * <p>
     * width：页面宽度。单位：点
     * height：页面高度。单位：点
     * 换算方式：点 = 毫米*8。例如:宽度76毫米，width=76*8;
     */
    private void printLabel() {
        try {
            printer.labelConfig().setPrintDirection(PrinterDirection.Normal);

            ILabelEdit labelEdit = printer.labelEdit();
            labelEdit.setColumn(1, 0);
            labelEdit.setLabelSize(600, 960);
            Bitmap bmp = BitmapFactory.decodeStream(getAssets().open("logo.bmp"));

            labelEdit.printImage(50,0,bmp);
            //字体32*32
            labelEdit.printText(152, 140, "55", "巴基斯坦货运系统", Rotation.Rotation0, 0, 3, 1, 0);
            //24&24
            labelEdit.printText(348, 180, "8", "2020-01-04 12:30", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printLine(20, 220, 730, 220, 1);
            labelEdit.printText(20, 240, "8", "订单号", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(192, 240, "8", "A00012454", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(20, 280, "8", "运输类型", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(192, 280, "8", "零担", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(20, 320, "8", "寄件人", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(192, 320, "8", "名称+联系方式+地址", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(20, 360, "8", "收件人", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(192, 360, "8", "名称+联系方式+地址", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printLine(20, 400, 730, 400, 1);
            labelEdit.printText(20, 420, "8", "发运车站", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(192, 420, "8", "南京", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(320, 420, "8", "运输里程", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(450, 420, "8", "214km", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(20, 460, "8", "到达车站", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(192, 460, "8", "苏州", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printLine(20, 500, 730, 500, 1);
            labelEdit.printText(20, 520, "8", "货物名称", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(192, 520, "8", "手机", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(320, 520, "8", "货物重量", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(450, 520, "8", "0.5kg", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(20, 560, "8", "单价", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(192, 560, "8", "40元/kg", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(320, 560, "8", "价格区间", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(450, 560, "8", "1-250km", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(20, 600, "8", "运费", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(192, 600, "8", "20元", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printLine(20, 640, 730, 640, 1);
            labelEdit.printText(20, 660, "8", "支付方式", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(192, 660, "8", "现金", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(20, 700, "8", "支付金额", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printText(192, 700, "8", "20元", Rotation.Rotation0, 0, 0, 0, 0);
            labelEdit.printBarcodeQR(192, 740, Rotation.Rotation0, "123123", QRLevel.QR_LEVEL_H.getLevel(), 160, QRMode.QR_MODE_ENHANCED.getMode());
            printer.labelControl().print(1, 1);
        } catch (Exception e) {
            LogUtils.e("printLabel error:" + e.getMessage());
        }

    }

    private void printImg() {
        try {
//            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.lion);
            Bitmap bmp = BitmapFactory.decodeStream(getAssets().open("BJST.bmp"));
            ILabelEdit labelEdit = printer.labelEdit();
            labelEdit.setLabelSize(600, 1000); //设置页面大小
            labelEdit.printImage(0, 0, bmp);//打印位图，设置x、y坐标
            printer.labelControl().print(1, 1);//执行打印
        } catch (Exception e) {
            LogUtils.e("打印图片出错：" + e.getMessage());
        }
    }

    private void printBitmap() {
//        String bitmap_name = "/sdcard/Download/test1.bmp";
//        hd_type = 1;
//        int error_code = testprint.TestPrintBitmap(pos_blue, printMode, bitmap_name, hd_type);
//        if (error_code != POS_SUCCESS) {
//            shortToast("Failed to print bitmap.");
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class BluetoothConnectActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            checkSelfPermission();
//            if (intent.getAction().equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
//                BluetoothDevice mBluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                try {
//                    //(三星)4.3版本测试手机还是会弹出用户交互页面(闪一下)，如果不注释掉下面这句页面不会取消但可以配对成功。(中兴，魅族4(Flyme 6))5.1版本手机两中情况下都正常
//                    //ClsUtils.setPairingConfirmation(mBluetoothDevice.getClass(), mBluetoothDevice, true);
//                    abortBroadcast();//如果没有将广播终止，则会出现一个一闪而过的配对框。
//                    //3.调用setPin方法进行配对...
//                    boolean ret = ClsUtils.setPin(mBluetoothDevice.getClass(), mBluetoothDevice, "0000");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }


            String action = intent.getAction(); //得到action
            LogUtils.e("action1=", action);
            BluetoothDevice btDevice = null;  //创建一个蓝牙device对象
            // 从Intent中获取设备对象
            btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {  //发现设备
                LogUtils.e("发现设备:" + "[" + btDevice.getName() + "]" + ":" + btDevice.getAddress());
                //还未配对过
                //同名设备如果有多个，第一个搜到的那个会被尝试。
                if (btDevice.getName() == null) {
                    return;
                }
                if (btDevice.getName().contains("BTP-P32") && btDevice.getAddress().equals("04:7F:0E:56:58:FA")) {
                    LogUtils.e("找到了BTP-P32 04:7F:0E:56:58:FA");
                    if (btDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                        try {
                            //通过工具类ClsUtils,调用createBond方法
                            ClsUtils.createBond(btDevice.getClass(), btDevice);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (btDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                        //已经配对过了
//                        if (interface_blue.OpenDevice(btDevice.getAddress()) == POS_SUCCESS) {
//                            cur_address = btDevice.getAddress();
//                            pos_blue = new POSSDK(interface_blue);
//                            cancelDiscovery();
//                            shortToast("Connection has been established you can send the data.");
//                            LogUtils.e("Connection has been established you can send the data.");
//                        } else {
//                            interface_blue.CloseDevice();
//                            cur_address = null;
//                            shortToast("Failed to connect device, please try again.");
//                            LogUtils.e("Failed to connect device, please try again.");
//                        }
                    }
                }
            } else if (action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) //再次得到的action，会等于PAIRING_REQUEST
            {
                LogUtils.e("action2=", action);
                if (btDevice.getName().contains("BTP-P32")) {
                    LogUtils.e("here", "OKOKOK");

                    try {
                        //1.确认配对
                        ClsUtils.setPairingConfirmation(btDevice.getClass(), btDevice, true);
                        //2.终止有序广播
                        LogUtils.i("order...", "isOrderedBroadcast:" + isOrderedBroadcast() + ",isInitialStickyBroadcast:" + isInitialStickyBroadcast());
                        abortBroadcast();//如果没有将广播终止，则会出现一个一闪而过的配对框。
                        //3.调用setPin方法进行配对...
                        boolean ret = ClsUtils.setPin(btDevice.getClass(), btDevice, "0000");
                        LogUtils.e("配对结果：" + ret);

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else
                    LogUtils.e("提示信息", "这个设备不是目标蓝牙设备");

            }
        }
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, BluetoothPrintActivity.class);
        context.startActivity(intent);
    }
}
