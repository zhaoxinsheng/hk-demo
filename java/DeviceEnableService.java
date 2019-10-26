package com.ts.hc_ctrl_demo.service;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.ts.hc_ctrl_demo.common.entity.ApiResult;
import com.ts.hc_ctrl_demo.hc_java_sdk.HCNetSDK;
import com.ts.hc_ctrl_demo.hc_java_sdk.entity.SDKInstance;
import org.springframework.stereotype.Service;

/**
 * 设备使能 管理 服务
 *  目前只处理是否要开启
 */
@Service
public class DeviceEnableService {

//    HCNetSDK.NET_DVR_SetDVRConfig strAlarmInfoV30 = new HCNetSDK.NET_DVR_ALARMINFO_V30();
     // 1 打开摄像头 2 关闭摄像头
     // 返回值 true 执行成功 false 执行失败
     public ApiResult enableDeviceCamera(NativeLong lUserID, int closeOrOpen) {
         if (lUserID.intValue() == -1) {
             return ApiResult.Error(205, "请先注册！");
         }

         IntByReference ibrBytesReturned = new IntByReference(0);
         HCNetSDK.NET_DVR_CARD_READER_CFG_V50 cardReaderCfg = new HCNetSDK.NET_DVR_CARD_READER_CFG_V50();
         cardReaderCfg.write();

         Pointer lpInbuferCfg = cardReaderCfg.getPointer();
         System.out.println(cardReaderCfg.size());
         boolean readResult = SDKInstance.HC.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_CARD_READER_CFG_V50, new NativeLong(1), lpInbuferCfg, cardReaderCfg.size(), ibrBytesReturned);
         if (!readResult)
         {
             System.out.println("获取设备人脸参数失败，错误号：" + SDKInstance.HC.NET_DVR_GetLastError());

         }
         cardReaderCfg.read();
         cardReaderCfg.dwSize = cardReaderCfg.size();
         cardReaderCfg.byEnable = 1;
         cardReaderCfg.write();
         boolean writeResult = SDKInstance.HC.NET_DVR_SetDVRConfig(lUserID, HCNetSDK.NET_DVR_SET_CARD_READER_CFG_V50, new NativeLong(1), lpInbuferCfg, cardReaderCfg.size());
         if (!writeResult)
         {
             System.out.println("设置设备人脸参数失败，错误号：" + SDKInstance.HC.NET_DVR_GetLastError());

         }

         //////////////// 上面是海康提供的代码
//         int version = SDKInstance.HC.NET_DVR_GetSDKVersion();
//
//         //===  start read
//         IntByReference ibrBytesReturned = new IntByReference(0);
//         HCNetSDK.NET_DVR_CARD_READER_CFG_V50 cardReaderCfg = new HCNetSDK.NET_DVR_CARD_READER_CFG_V50();
//         cardReaderCfg.write();
//         Pointer lpInbuferCfg = cardReaderCfg.getPointer();
//         System.out.println(cardReaderCfg.size());
//         if (!SDKInstance.HC.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_CARD_READER_CFG_V50, new NativeLong(1), lpInbuferCfg, cardReaderCfg.size(), ibrBytesReturned))
//         {
//             System.out.println("获取设备人脸参数失败，错误号：" + SDKInstance.HC.NET_DVR_GetLastError());
//             return ApiResult.Error(500, "获取摄像头使能信息报错：" + SDKInstance.HC.NET_DVR_GetLastError());
//         }
//         System.out.println("byEnable = " + cardReaderCfg.byEnable);
         //=== end  read


         ///===  上面是获取配置值
//         HCNetSDK.NET_DVR_CARD_READER_CFG_V50 cardWriteCfg = new HCNetSDK.NET_DVR_CARD_READER_CFG_V50();
//         wrapWriteCFG(cardWriteCfg,cardReaderCfg);
//         // cardReaderCfg.byEnable = 0xFFFFFFFF;
//         cardWriteCfg.write();
//         Pointer writeBuferCfg = cardWriteCfg.getPointer();
//         System.out.println(cardWriteCfg.size());
////         boolean  NET_DVR_SetDVRConfig(NativeLong lUserID, int dwCommand, NativeLong lChannel, Pointer lpInBuffer, int dwInBufferSize);
//         int setCode = HCNetSDK.NET_DVR_SET_CARD_READER_CFG_V50; // 2506
//         boolean result =  SDKInstance.HC.NET_DVR_SetDVRConfig(lUserID,setCode,new NativeLong(1),writeBuferCfg, cardWriteCfg.size());
//         if (!result)
//         {
//             System.out.println("获取设备人脸参数失败，错误号：" + SDKInstance.HC.NET_DVR_GetLastError());
//             return ApiResult.Error(500, "获取摄像头使能信息报错：" + SDKInstance.HC.NET_DVR_GetLastError());
//         }
//         System.out.println("byEnable2 = " + cardWriteCfg.byEnable);
//         int enable = cardReaderCfg.byEnable;
//=== 以上是设置卡信息的 代码
         return ApiResult.Ok();
  }

    /**
     * 写包装类
     * @param cardWriteCfg
     * @param cardReaderCfg
     */
    public void wrapWriteCFG(HCNetSDK.NET_DVR_CARD_READER_CFG_V50 cardWriteCfg ,HCNetSDK.NET_DVR_CARD_READER_CFG_V50 cardReaderCfg) {
        cardWriteCfg. dwSize                                  = cardReaderCfg.            dwSize                                       ;
        // cardWriteCfg. byEnable                                = 0xFFFFFFFE                                   ;
       // cardWriteCfg. byEnable                                = 1;
        cardWriteCfg. byEnable                                = cardReaderCfg.byEnable;
                cardWriteCfg. byCardReaderType                        = cardReaderCfg.            byCardReaderType                             ;
        cardWriteCfg. byOkLedPolarity                         = cardReaderCfg.            byOkLedPolarity                              ;
        cardWriteCfg. byErrorLedPolarity                      = cardReaderCfg.            byErrorLedPolarity                           ;
        cardWriteCfg. byBuzzerPolarity                        = cardReaderCfg.            byBuzzerPolarity                             ;
        cardWriteCfg. bySwipeInterval                         = cardReaderCfg.            bySwipeInterval                              ;
        cardWriteCfg. byPressTimeout                          = cardReaderCfg.            byPressTimeout                               ;
        cardWriteCfg. byEnableFailAlarm                       = cardReaderCfg.            byEnableFailAlarm                            ;
        cardWriteCfg. byMaxReadCardFailNum                    = cardReaderCfg.            byMaxReadCardFailNum                         ;
        cardWriteCfg. byEnableTamperCheck                     = cardReaderCfg.            byEnableTamperCheck                          ;
        cardWriteCfg. byOfflineCheckTime                      = cardReaderCfg.            byOfflineCheckTime                           ;
        cardWriteCfg. byFingerPrintCheckLevel                 = cardReaderCfg.            byFingerPrintCheckLevel                      ;
        cardWriteCfg. byUseLocalController                    = cardReaderCfg.            byUseLocalController                         ;
        cardWriteCfg. byRes1                                  = cardReaderCfg.            byRes1                                       ;
        cardWriteCfg.        wLocalControllerID               = cardReaderCfg.                   wLocalControllerID                    ;
        cardWriteCfg.        wLocalControllerReaderID         = cardReaderCfg.                   wLocalControllerReaderID              ;
        cardWriteCfg.        wCardReaderChannel               = cardReaderCfg.                   wCardReaderChannel                    ;
        cardWriteCfg. byFingerPrintImageQuality               = cardReaderCfg.            byFingerPrintImageQuality                    ;
        cardWriteCfg. byFingerPrintContrastTimeOut            = cardReaderCfg.            byFingerPrintContrastTimeOut                 ;
        cardWriteCfg. byFingerPrintRecogizeInterval           = cardReaderCfg.            byFingerPrintRecogizeInterval                ;
        cardWriteCfg. byFingerPrintMatchFastMode              = cardReaderCfg.            byFingerPrintMatchFastMode                   ;
        cardWriteCfg. byFingerPrintModuleSensitive            = cardReaderCfg.            byFingerPrintModuleSensitive                 ;
        cardWriteCfg. byFingerPrintModuleLightCondition       = cardReaderCfg.            byFingerPrintModuleLightCondition            ;
        cardWriteCfg. byFaceMatchThresholdN                   = cardReaderCfg.            byFaceMatchThresholdN                        ;
        cardWriteCfg. byFaceQuality                           = cardReaderCfg.            byFaceQuality                                ;
        cardWriteCfg. byFaceRecogizeTimeOut                   = cardReaderCfg.            byFaceRecogizeTimeOut                        ;
        cardWriteCfg. byFaceRecogizeInterval                  = cardReaderCfg.            byFaceRecogizeInterval                       ;
        cardWriteCfg.        wCardReaderFunction              = cardReaderCfg.                   wCardReaderFunction                   ;
        cardWriteCfg.           byCardReaderDescription       = cardReaderCfg.                      byCardReaderDescription            ;
        cardWriteCfg.        wFaceImageSensitometry           = cardReaderCfg.                   wFaceImageSensitometry                ;
        cardWriteCfg. byLivingBodyDetect                      = cardReaderCfg.            byLivingBodyDetect                           ;
        cardWriteCfg. byFaceMatchThreshold1                   = cardReaderCfg.            byFaceMatchThreshold1                        ;
        cardWriteCfg.        wBuzzerTime                      = cardReaderCfg.                   wBuzzerTime                           ;
        cardWriteCfg. byFaceMatch1SecurityLevel               = cardReaderCfg.            byFaceMatch1SecurityLevel                    ;
        cardWriteCfg. byFaceMatchNSecurityLevel               = cardReaderCfg.            byFaceMatchNSecurityLevel                    ;
        cardWriteCfg. byEnvirMode                             = cardReaderCfg.            byEnvirMode                                  ;
        cardWriteCfg. byLiveDetLevelSet                       = cardReaderCfg.            byLiveDetLevelSet                            ;
        cardWriteCfg. byLiveDetAntiAttackCntLimit             = cardReaderCfg.            byLiveDetAntiAttackCntLimit                  ;
        cardWriteCfg. byEnableLiveDetAntiAttack               = cardReaderCfg.            byEnableLiveDetAntiAttack                    ;
        cardWriteCfg. bySupportDelFPByID                      = cardReaderCfg.            bySupportDelFPByID                           ;
        cardWriteCfg. byFaceContrastMotionDetLevel            = cardReaderCfg.            byFaceContrastMotionDetLevel                 ;
        cardWriteCfg. byDayFaceMatchThresholdN                = cardReaderCfg.            byDayFaceMatchThresholdN                     ;
        cardWriteCfg. byNightFaceMatchThresholdN              = cardReaderCfg.            byNightFaceMatchThresholdN                   ;
        cardWriteCfg. byFaceRecogizeEnable                    = cardReaderCfg.            byFaceRecogizeEnable                         ;
        cardWriteCfg.           byRes3                        = cardReaderCfg.                      byRes3                             ;
        cardWriteCfg. byDefaultVerifyMode                     = cardReaderCfg.            byDefaultVerifyMode                          ;
        cardWriteCfg.dwFingerPrintCapacity                     = cardReaderCfg.   dwFingerPrintCapacity                                 ;
        cardWriteCfg.dwFingerPrintNum                          = cardReaderCfg.   dwFingerPrintNum                                      ;
        cardWriteCfg. byEnableFingerPrintNum                  = cardReaderCfg.            byEnableFingerPrintNum                       ;
        cardWriteCfg.           byRes                         = cardReaderCfg.                      byRes                              ;
    }
  }
