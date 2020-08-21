package com.why.controller.bluetooth

import android.util.Log
import com.why.controller.activities.ControllerActivity
import com.why.controller.activities.MainActivity
import java.util.regex.Pattern


class DataSender : Thread() {
    lateinit var command:List<String>

    override fun run() {
        println("datasender running")

        while (true) {
           if (isInterrupted){
                Log.d("ZZZ","interrupted I am dead now "+hashCode());
                return
            }
            //println(ControllerActivity.cmd.toString())
            if (ControllerActivity.cmd.isNotEmpty()) {
                Log.d("ZZZ","Command: "+ ControllerActivity.cmd);
                command = ControllerActivity.cmd.toString().split("\u0000") //this is escape character in kotlin because kotlin is fucking good
                for (i in command){
                    control(i)
                }
                ControllerActivity.cmd.setLength(0);


            }
        }
    }

    fun control(cmd:String){
        val cmmd=cmd.split(" ")

        when(cmmd[0]){
            "D"->{
                Log.d("ZZZ", "Pressing down "+cmmd[1])
                keyDown(cmmd[1])
            }
            "U"->{
                keyUp(cmmd[1])
            }
            "T"->{
                keyPress(cmmd[1])
            }
        }
    }

    fun keyDown(cmd:String){
        println(cmd)
        when(cmd){
            "m1"->{
                Main.mouse?.sendLeftClickOn()
            }
            "m2"->{
                Main.mouse?.sendRightClickOn()
            }
            "m3"->{
                //TODO: middle click in HID ident and send data
            }
            "alt"->{
                Main.keyboard?.keyboardReport?.rightAlt=true
            }
            "shift"->{
                Main.keyboard?.keyboardReport?.rightShift=true
            }
            "ctrl"->{
                Main.keyboard?.keyboardReport?.rightControl=true
            }
            else->{
                Log.d("ZZZ",cmd)
                Log.d("ZZZ", KeyboardReport.KeyEventMap[cmd].toString())
                Main.keyboard?.sendKeyOn(KeyboardReport.KeyEventMap[cmd])
            }
        }
    }

    fun keyUp(cmd:String){
        when(cmd){
            "m1"->{
                Main.mouse?.sendLeftClickOff()
            }
            "m2"->{
                Main.mouse?.sendRightClickOff()
            }
            "m3"->{
                //TODO: middle click in HID ident and send data
            }
            "alt"->{
                Main.keyboard?.keyboardReport?.rightAlt=false
            }
            "shift"->{
                Main.keyboard?.keyboardReport?.rightShift=false
            }
            "ctrl"->{
                Main.keyboard?.keyboardReport?.rightControl=false
            }
            else->{
                Main.keyboard?.sendKeyOff()
            }
        }
    }

    fun keyPress(cmd:String){
        Main.keyboard?.sendKeyOn(KeyboardReport.KeyEventMap[cmd])
        //Thread.sleep(1) //uncomment if latency screws stuff up
        Main.keyboard?.sendKeyOff()
    }
}

