package uitestlib.walle.modules

import geb.navigator.Navigator

/**
 * Created by QingHuan on 2019/11/14 22:44
 */
class DataRangeInput extends Field{
    static content = {
        //开始日期输入框
        startDateInputField {String labelName ->controlDiv(labelName).$('span input',type:"text",0)}

        startDateInputFieldByNavigator { Navigator lbDiv -> controlDivByNavigator(lbDiv).$('span input',type:"text",0)}
        //结束日期输入框

        endDateInputField{String labelName ->controlDiv(labelName).$('span input',type:"text",1)}
        endDateInputFieldByNavigator { Navigator lbDiv -> controlDivByNavigator(lbDiv).$('span input',type:"text",1)}
    }


    //清除某个日期输入字段已有的内容
    def clearDataRangeForField(String fieldName){
        log.info("清除${fieldName}字段已有的日期")
        Navigator lbDiv = labelDiv(fieldName)
        assert lbDiv,"找不到 ${fieldName}字段"
        clearDataRangeForField(lbDiv)
    }


    def clearDataRangeForField(Navigator lvDiv){
        Navigator trigger = controlDivByNavigator(lvDiv).$('i.next-icon')
        assert trigger,"日期组件上没有找到清除内容的按钮"
        if(trigger.attr('class').contains('next-icon-delete-filling')){
            //点击第一次清除已有数据
            stableClick(trigger)
        }
    }

    def clearDataRangeForNoField(Navigator ctrlDiv){
        //日期图层展开按钮
        Navigator trigger = ctrlDiv.$('i.next-icon')
        assert trigger,"日期组件上没有找到清除内容的按钮"
        if(trigger.attr('class').contains('next-icon-delete-filling')){
            //点击第一次清除已有数据
            stableClick(trigger)
        }
    }

    /**
     * 输入日期
     * @param fieldName 标记文本
     * @param startData 开始时间
     * @param endData 结束时间
     * @return
     */
    def inputDataRangeForField(String fieldName,String startData,String endData){
        log.info("想日期字段输入值：${fieldName},${startData},${endData}")
        Navigator lvDiv = labelDiv(fieldName)
        assert lvDiv,"找不到 ${fieldName}字段"
        inputDataRangeForField(lvDiv,startData,endData)
    }


    def inputDataRangeForField(Navigator lbDiv,String startData,String endData){
        log.info("想日期字段输入值：${startData}，${endData}")
        Navigator trigger = controlDivByNavigator(lbDiv).$('i.next-icon')
        triggerUntilOpened(trigger)

        Navigator start = overlay.$('input',type:"text",0)
        Navigator end = overlay.$('input',type:"text",1)
        Navigator confirmButton = overlay.$('div.next-data-picker-quick-tool button')
        if (start){
            forceClearAndSetValue(start,startData)
            forceClearAndSetValue(end,endData)
            stableClick(confirmButton)
        }
        else {
            start = overlay.$('div.next-range-picker-panel-input-start-date input')
            end = overlay.$('div.next-range-picker-panel-input-end-date input')
            confirmButton = overlay.$('button',text:"确")

            forceClearAndSetValue(start,startData)
            forceClearAndSetValue(end,endData)
            stableClick(confirmButton)
        }
        log.debug('输入完成')
    }


    def inputDataRangeForField(String fieldName,String startData,String startHsm,String endData,String endHsm){
        log.info("向日期字段输入值：${fieldName} ,${startData},${ startHsm}")
        Navigator lbDiv = labelDiv(fieldName)
        assert lbDiv,"找不到 ${fieldName}字段"
        inputDataRangeForField(lbDiv,startData,startHsm,endData,endHsm)
    }

    def inputDataRangeForField(Navigator lbDiv,String startData,String startHsm,String endData,String endHsm){
        log.info("向日期字段输入值： ,${startData},${ startHsm}")
        //日期图层展开按钮
        Navigator trigger = controlDivByNavigator(lbDiv).$('i.next-icon')
        triggerUntilOverlayOpened(trigger)

        Navigator start = overlay.$('input',type:"text",0)
        Navigator starthms = overlay.$('input',type:"text",1)
        Navigator end = overlay.$('input',type:"text",2)
        Navigator endhms = overlay.$('input',type:"text",3)
        Navigator confirmButton = overlay.$('div.next-data-picker-quick-tool button')

        forceClearAndSetValue(start,startData)
        forceClearAndSetValue(end,endData)

        stableClick(starthms)
        forceClearAndSetValue(starthms,startHsm)

        stableClick(endhms)
        forceClearAndSetValue(endhms,endHsm)

        stableClick(confirmButton)
        log.debug("输入完成")
    }

    def inputDataRangeForNoField(Navigator ctrlDiv,String startDate,String endDate){
        log.info("向日期字段输入值：${ startDate},${endDate}")
        //日期图层展开按钮
        Navigator trigger = ctrlDiv.$('i.next-icon')
        triggerUntilOverlayOpened(trigger)

        Navigator start = overlay.$('input',type:"text",0)
        Navigator end = overlay.$('input',type:"text",1)

        Navigator confirmButton = overlay.$('div.next-data-picker-quick-tool button')
        forceClearAndSetValue(start,startDate)
        forceClearAndSetValue(end,endDate)
        if (confirmButton){
            stableClick(confirmButton)
        }
        log.debug("输入完成")
    }

    def inputDataRangeForNoField(Navigator ctrlDiv,String startData,String startHsm,String endData,String endHsm) {
        log.info("向日期字段输入值： ,${startData},${startHsm}")
        //日期图层展开按钮
        Navigator trigger = ctrlDiv.$('i.next-icon')
        triggerUntilOverlayOpened(trigger)

        Navigator start = overlay.$('input', type: "text", 0)
        Navigator starthms = overlay.$('input', type: "text", 1)
        Navigator end = overlay.$('input', type: "text", 2)
        Navigator endhms = overlay.$('input', type: "text", 3)
        Navigator confirmButton = overlay.$('div.next-data-picker-quick-tool button')

        forceClearAndSetValue(start, startData)

        stableClick(starthms)
        forceClearAndSetValue(starthms, startHsm)

        forceClearAndSetValue(end, endData)
        stableClick(endhms)
        forceClearAndSetValue(endhms, endHsm)

        stableClick(confirmButton)
        log.debug("输入完成")
    }

    /**
     * 获取到页面日期值
     * @param fieldName
     * @param fieldIndex
     * @return
     */
    List<String> getDateForField(String fieldName,Integer fieldIndex = 0){
        log.info("获取给定页面字段的值：${fieldName},${fieldIndex}")
        List<String> resule = []
        Navigator allInputField = controlDiv(fieldName,fieldIndex).$('span input',type:'text')
        allInputField.each {
            resule.add(it.attr('value'))
        }
        log.info("获取到的值$resule")
        return resule
    }

}
