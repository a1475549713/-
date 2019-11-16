package uitestlib.walle.modules

import geb.navigator.Navigator

/**
 * 时间输入组件：时 分 秒
 * Created by QingHuan on 2019/11/16 18:51
 */
class TimeInput extends Field{

    def clearTimeForField(String fieldName){
        log.info("清除${fieldName}字段中已有的时间")
        Navigator lbDiv = labelDiv(fieldName)
        assert lbDiv,"页面上不存在${fieldName}字段"
        clearTimeForField(lbDiv)
    }

    def clearTimeForField(Navigator lbDiv){
        Navigator tigger = controlDivByNavigator(lbDiv).$('i.next-icon')
        if (tigger.attr('class').contains('next-icon-delete-filling')){
            stableClick(tigger)
        }
    }

    def clearTimeForNoField(Navigator lbDiv){
        Navigator tigger = lbDiv.$('i.next-icon')
        if (tigger.attr('class').contains('next-icon-delete-filling')){
            stableClick(tigger)
        }
    }


    def inputTimeForField(String fieldName,String theTime){
        log.info("为指定的时间字段输入给定的值${fieldName},${theTime}")
        Navigator lbDiv = labelDiv(fieldName)
        assert lbDiv,"页面上不存在${fieldName}字段"
        inputTimeForField(lbDiv,theTime)
    }

    def inputTimeForField(Navigator lbDiv,String theTime){
        log.info("为指定的时间字段输入给定的值:${theTime}")
        Navigator trigger = controlDivByNavigator(lbDiv).$('i.next-icon')
        triggerUntilOverlayOpened(trigger)
        Navigator time = overlay.$('input',type:"text",0)
        forceClearAndSetValue(time,theTime)
        lbDiv.click()
        log.info("输入完成")
    }

    def inputTimeForNoField(Navigator lbDiv,String theTime){
        log.info("为指定的时间字段输入给定的值:${theTime}")
        Navigator trigger = lbDiv.$('i.next-icon')
        triggerUntilOverlayOpened(trigger)

        Navigator time = overlay.$('input',type:"text",0)
        forceClearAndSetValue(time,theTime)
        //使弹出层消失
        $('body').click()
        log.info("输入完成")
    }


    def getTimeForField(String fieldName,Integer fieldIndex=0) {
        log.info("获取页面给定字段的值：${fieldName},${fieldIndex}")
        Navigator allInputField = controlDiv(fieldName, fieldIndex).$('span input', type: 'text')
        String result = allInputField.attr('value')
        log.info("获取到的值为${result}")
        return result
    }
    def inputTimeOnOpenedOverlay(String theTime){
        log.info("在已经打开的图层中输入时间:${theTime}")
        Navigator time = overlay.$('input',type:"text",0)
        if (time?.attr("value")){
            forceClearAndSetValue(time,theTime)
        }
        else {
            time<<theTime
        }
        $('body').click()
        log.info("输入完成")
    }

}
