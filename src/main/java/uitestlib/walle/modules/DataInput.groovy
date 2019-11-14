package uitestlib.walle.modules

import geb.navigator.Navigator

/**
 * Created by QingHuan on 2019/11/13 21:57
 */
class DataInput extends Field {

    static content = {
        //开始日期输入框
        startDateInputField {String labelName ->controlDiv(labelName).$('span input',type:"text",0)}
        //开始日期输入框
        startDateInputFieldByNavigator { Navigator lbDiv -> controlDivByNavigator(lbDiv).$('span input',type:"text",0)}
    }

    /**
     * 清除某个日期输入字段已有的内容
     * @param fieldName
     * @return
     */
    def clearDtaForField(String fieldName){
        log.info("清除${fieldName}字段已有的日期")
        Navigator lbDiv = labelDiv(fieldName)
        assert lbDiv,"找不到 ${fieldName}字段"
        clearDtaForField(lbDiv)
    }

    def clearDtaForField(Navigator lvDiv){
        Navigator trigger = controlDivByNavigator(lvDiv).$('i.next-icon')
        assert trigger,"日期组件上灭有找到清除内容的按钮"
        if(trigger.attr('class').contains('next-icon-delete-filling')){
            //点击第一次清除已有数据
            stableClick(trigger)
        }
    }

    def clearDtaForNoField(Navigator ctrlDiv){

        Navigator trigger = ctrlDiv.$('i.next-icon')
        assert trigger,"日期组件上灭有找到清除内容的按钮"
        if(trigger.attr('class').contains('next-icon-delete-filling')){
            //点击第一次清除已有数据
            stableClick(trigger)
        }
    }

    /**
     * 向指定日期字段的元素中输入给定的值
     * @param fieldName
     * @param startData
     * @return
     */

    def inputDataForField(String fieldName,String startData){
        log.info("想日期字段输入值：${fieldName},${startData}")
        Navigator lvDiv = labelDiv(fieldName)
        assert lvDiv,"找不到 ${fieldName}字段"
        inputDataForField(lvDiv,startData)
    }

    def inputDataForField(Navigator lbDiv,String startData){
        log.info("想日期字段输入值：${startData}")
        Navigator trigger = controlDivByNavigator(lbDiv).$('i.next-icon')
        triggerUntilOverlayOpened(trigger)
        Navigator start = findTextTypeInputElement(overlay,0)
        Navigator confirmButton = overlay.$('div.next-data-picker-quick-tool button')
        forceClearAndSetValue(start,startData)
       if (confirmButton){
           stableClick(confirmButton)
       }
       else {
           log.info("日期组件上没有确认按钮，点击空白处来关闭")
           clickWhiteArea(lbDiv)
       }
        log.debug('输入完成')
    }

    def inputDataForField(String fieldName,String startData,String startHsm){
        log.info("向日期字段输入值：${fieldName} ,${startData},${ startHsm}")
        Navigator lbDiv = labelDiv(fieldName)
        assert lbDiv,"找不到 ${fieldName}字段"
        inputDataForField(lbDiv,startData,startHsm)

    }

    def inputDataForField(Navigator lbDiv,String startData,String startHsm){
        log.info("向日期字段输入值： ,${startData},${ startHsm}")
        //日期图层展开按钮
        Navigator trigger = controlDivByNavigator(lbDiv).$('i.next-icon')
        triggerUntilOverlayOpened(trigger)

        Navigator start = findTextTypeInputElement(overlay,0)
        Navigator starthms = findTextTypeInputElement(overlay,1)
        Navigator confirmButton = overlay.$('div.next-data-picker-quick-tool button')
        forceClearAndSetValue(start,startData)
        stableClick(starthms)
        forceClearAndSetValue(starthms,startHsm)
        if (confirmButton){
            stableClick(confirmButton)
        }
        log.debug("输入完成")
    }

    /**
     * 向没有标签的日期元素中输入给定的值
     * @param ctrlDiv
     * @param startDate
     * @return
     */
    def inputDataForNoField(Navigator ctrlDiv,String startDate){
        log.info("向日期字段输入值：${ startDate}")
        //日期图层展开按钮
        Navigator trigger = ctrlDiv.$('i.next-icon')
        triggerUntilOverlayOpened(trigger)

        Navigator start = findTextTypeInputElement(overlay,0)
        Navigator confirmButton = overlay.$('div.next-data-picker-quick-tool button')
        forceClearAndSetValue(start,startDate)
        if (confirmButton){
            stableClick(confirmButton)
        }
        log.debug("输入完成")
    }

    def inputDataForNoField(Navigator ctrlDiv,String startData,String startHsm){
        log.info("向日期字段输入值：${ startDate}")
        //日期图层展开按钮
        Navigator trigger = ctrlDiv.$('i.next-icon')
        if (trigger.size() == 2 ){
            triggerUntilOverlayOpened(trigger[0])
            inputDataOnOpenedOverlay(startData)

            triggerUntilOverlayOpened(trigger[1])
            inputDataOnOpenedOverlay(startHsm)

        }
        else {
            triggerUntilOverlayOpened(trigger)

            Navigator start = findTextTypeInputElement(overlay,0)
            Navigator starthms = findTextTypeInputElement(overlay,1)
            Navigator confirmButton = overlay.$('div.next-data-picker-quick-tool button')
            forceClearAndSetValue(start,startData)
            stableClick(starthms)
            forceClearAndSetValue(starthms,startHsm)
            if (confirmButton){
                stableClick(confirmButton)
            }
            log.debug("输入完成")
        }
    }


    List<String> getDateForField(String fieldName,Integer fieldIndex = 0){
        log.info("获取给定页面字段的值：${fieldName},${fieldIndex}")
        List<String> resule = []
        Navigator allInputField = controlDiv(fieldName,fieldIndex).$('span input')
        allInputField.each {
            resule.add(it.attr('value'))
        }
        log.info("获取到的值$resule")
        return resule
    }

    /**
     * 在已经打开的图层中输入日期
     * @param startData
     * @return
     */
    def inputDataOnOpenedOverlay(String startData){
        log.info("在已经打开的图层找你输入日期${startData}")

        Navigator start = findTextTypeInputElement(overlay,0)
        Navigator confirmButton = overlay.$('div.next-data-picker-quick-tool button')
        forceClearAndSetValue(start,startData)
        if (confirmButton){
            stableClick(confirmButton)
        }
        log.debug("输入完成")
    }

    def inputDateOnOpenedOverlay(String startDate){
        log.info("在已经打开的图层找你输入日期${startDate}")
        Navigator start = findTextTypeInputElement(overlay,0)
        Navigator confirmButton = overlay.$('div.next-data-picker-quick-tool button')
        forceClearAndSetValue(start,startDate)
        if (confirmButton){
            stableClick(confirmButton)
        }
        log.debug("输入完成")

    }
    def inputDataOnOpenedOverlay(String startData,String startHsm){
        log.info("在已经打开的图层找你输入日期${startData},${startHsm}")

        Navigator start = findTextTypeInputElement(overlay,0)
        Navigator starthms = findTextTypeInputElement(overlay,1)
        Navigator confirmButton = overlay.$('div.next-data-picker-quick-tool button')
        assert start,"日期组件上面没有找到开始日期输入框"
        assert starthms,"日期组件上面没有找到时分秒输入框"
        forceClearAndSetValue(start,startData)
        stableClick(starthms)
        forceClearAndSetValue(starthms,startHsm)
        if (confirmButton){
            stableClick(confirmButton)
        }
        log.debug("输入完成")

    }

}
