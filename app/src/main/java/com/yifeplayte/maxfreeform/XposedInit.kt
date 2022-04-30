package com.yifeplayte.maxfreeform

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookMethod
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage

class XposedInit : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        EzXHelperInit.initHandleLoadPackage(lpparam)
        when (lpparam.packageName) {
            "android" -> {
                maxFreeFormCount(lpparam)
            }
            "com.miui.home" -> {
                canTaskEnterSmallWindow(lpparam)
                canTaskEnterMiniSmallWindow(lpparam)
            }
        }
    }

    private fun maxFreeFormCount(lpparam: XC_LoadPackage.LoadPackageParam) {
        try {
            findMethod("com.android.server.wm.MiuiFreeFormStackDisplayStrategy") {
                name == "getMaxMiuiFreeFormStackCount"
            }.hookMethod {
                after { param ->
                    param.result = 256
                }
            }
            XposedBridge.log("MaxFreeForm: Hook getMaxMiuiFreeFormStackCount success!")
        } catch (e: Throwable) {
            XposedBridge.log("MaxFreeForm: Hook getMaxMiuiFreeFormStackCount failed!")
        }
    }

    /*****
    private fun multiWindowSupport(lpparam: XC_LoadPackage.LoadPackageParam) {
        try {
            findMethod("android.util.MiuiMultiWindowUtils") {
                name == "multiFreeFormSupported"
            }.hookMethod {
                after { param ->
                    param.result = true
                }
            }
            XposedBridge.log("MaxFreeForm: Hook multiFreeFormSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("MaxFreeForm: Hook multiFreeFormSupported failed!")
        }
    }
    *****/

    private fun canTaskEnterSmallWindow(lpparam: XC_LoadPackage.LoadPackageParam) {
        try {
            findMethod("com.miui.home.launcher.RecentsAndFSGestureUtils") {
                name == "canTaskEnterSmallWindow"
            }.hookMethod {
                after { param ->
                    param.result = true
                }
            }
            XposedBridge.log("MaxFreeForm: Hook canTaskEnterSmallWindow success!")
        } catch (e: Throwable) {
            XposedBridge.log("MaxFreeForm: Hook canTaskEnterSmallWindow failed!")
        }
    }

    private fun canTaskEnterMiniSmallWindow(lpparam: XC_LoadPackage.LoadPackageParam) {
        try {
            findMethod("com.miui.home.launcher.RecentsAndFSGestureUtils") {
                name == "canTaskEnterMiniSmallWindow"
            }.hookMethod {
                after { param ->
                    param.result = true
                }
            }
            XposedBridge.log("MaxFreeForm: Hook canTaskEnterMiniSmallWindow success!")
        } catch (e: Throwable) {
            XposedBridge.log("MaxFreeForm: Hook canTaskEnterMiniSmallWindow failed!")
        }
    }

}