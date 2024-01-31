package com.ssafy.travelcollector.config

class PermissionChecker {

//    fun checkPermissions(context: Context){
//        //BeaconManager 지정
//        beaconManager = BeaconManager.getInstanceForApplication(context)
//        //estimo 비컨을 분석 하도록 하기 위하여 beacon parser 오프셋, 버전등을 setLayout으로 지정한다.
////		m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24
////		설명: 0 ~ 1 바이트는 제조사를 나타내는 필드로 파싱하지 않는다.
////		2~3 바이트는 0x02, 0x15 이다.
////		4~19 바이트들을 첫번째 ID로 매핑한다.(UUID)
////		20~21 바이트들을 두번째 ID로 매핑한다.(Major)
////		22-23 바이트들을 세번째 ID로 매핑한다.(Minor)
////		24~24 바이트들을 txPower로 매핑한다.
//        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
//        bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//        bluetoothAdapter = bluetoothManager.adapter
//
//        if (bluetoothAdapter == null || !bluetoothAdapter!!.isEnabled) {
//            Toast.makeText(context, "블루투스 기능을 확인해 주세요.", Toast.LENGTH_SHORT).show()
//            val bleIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            (context as MainActivity).startActivityForResult(bleIntent, 1)
//        }
//
//        checker = PermissionChecker(context)
//
//        /* permission check */
//        if (!checker.checkPermission(runtimePermissions)) {
//            checker.permitted = object : PermissionListener {
//                override fun onGranted() {
//                    //퍼미션 획득 성공일때
//                }
//            }
//            checker.requestPermissionLauncher.launch(runtimePermissions)
//        } else { //이미 전체 권한이 있는 경우
//        }
//        /* permission check */
//
//    }
//
//    interface PermissionListener{
//        fun onGranted()
//    }
//
//    inner class PermissionChecker(private val context: Context) {
//
//        lateinit var permitted: PermissionListener
//
//        // 권한 체크
//        fun checkPermission(permissions: Array<String>): Boolean {
//            for (permission in permissions) {
//                if (ActivityCompat.checkSelfPermission(
//                        context,
//                        permission
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    return false
//                }
//            }
//
//            return true
//        }
//
//        // 권한 호출한 이후 결과받아서 처리할 Launcher (startPermissionRequestResult )
//        val requestPermissionLauncher = (context as AppCompatActivity).registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()){
//            Log.d(TAG, "requestPermissionLauncher: 건수 : ${it.size}")
//
//            if(it.values.contains(false)){ //false가 있는 경우라면..
//                Toast.makeText(context, "권한이 부족합니다.", Toast.LENGTH_SHORT).show()
//            }else{
//                Toast.makeText(context, "모든 권한이 허가되었습니다.", Toast.LENGTH_SHORT).show()
//                permitted.onGranted()
//            }
//        }
//
//    }

}