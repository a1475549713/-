package uitestlib.walle.modules

import geb.navigator.Navigator
import uitestlib.uicommon.modules.CheckBoxSelector

/**
 * Created by QingHuan on 2019/11/14 23:23
 */
class TextInput  extends  Field{
    static content = {
        textInputField {String labelName,int index =0->
            Navigator ctrlDiv = controlDiv(labelName,index)
            Navigator normalInput = ctrlDiv.$('input',type:'text',0)
            if (normalInput){
                return normalInput
            }
            else {
                log.debug("没有找到'input',type:'text'元素，查找标签为input切勿显示type元素")
                return ctrlDiv.$('input').find{
                    String theType = it.attr('type')
                    return theType == ''||theType == null || theType == 'text' || theType=='password'
                }
            }
        }
        textInputFieldByNavigator {Navigator lbDiv ->
            Navigator ctrlDiv = controlDivByNavigator(lbDiv)
            Navigator normalInput = ctrlDiv.$('input',type: "text",0)
            if (normalInput){
                return normalInput
            }
            else {
                log.debug("没有找到'input',type:'text'元素，查找标签为input切勿显示type元素")
                return ctrlDiv.$('input').find{
                    String theType = it.attr('type')
                    return theType == ''||theType == null || theType == 'text' || theType=='password'
                }
            }
        }
        openedOverlay(wait:2){$('div.next-overlay-wrapper.opened',-1)}
    }


    /**
     * 向指定字段的元素中输入给定的值
     * @param fieldName
     * @param value
     * @param index
     * @return
     */
    def inputValueForField(String fieldName,String value,int index=0){
        log.info("向页面输入值：${fieldName},${index},${value}")
        Navigator lvDiv = labelDiv(fieldName,index)
        assert lvDiv,"找不到 ${fieldName}字段"
        inputValueForField(lvDiv,value)
    }

    def inputValueForField(Navigator lbDiv,String value){
        log.info("向页面输入值：,${value}")
        Navigator niv = textInputFieldByNavigator(lbDiv)
        assert niv,"没有找到页面元素${lbDiv}"
        //兼容，输入字段中嵌入级联选择器
        String placeHolder = niv.attr('placeholder')
        if (!placeHolder){
            log.info("该字段中没有placeholder，检查一下是否有打开的弹层进行级联选择")
            Navigator openedOverlay = findNewOpendOverlay {niv.click();sleep(300)}
            if (openedOverlay){
                //先取出勾选所有的
                Navigator cascader = openedOverlay.$('div.next-cascader')
                if (cascader){
                    Navigator allCheckBox = cascader.$('input',type:'checkbox')
                    allCheckBox.each {
                        if (CheckBoxSelector.isChecked(it)){
                            it.click()
                            log.debug('清空已选的复选框')
                        }
                    }

                    Navigator checkbox = cascader.$('span',text:value).closest('li').$('input',type:'checkbox')
                    Boolean checked = CheckBoxSelector.isChecked(checkbox)
                    if (!checked){
                        checkbox.click()
                    }
                    else {
                        log.info("指定的内容已处于勾选状态，不需要再勾选${value}")
                    }
                    openedOverlay.$('button',text:'确认').click()
                }
                else {
                    log.error("有打开的弹出层，但是不是级联选择元素，不知道如何处理，现在尝试一次普通输入")
                    forceClearAndSetValue(niv,value,true)
                }
            }else {
                log.debug("没有打开的弹出层，任然当做文本来梳理")
                forceClearAndSetValue(niv,value,true)
            }
        }else {
            forceClearAndSetValue(niv,value,true)
        }
        log.debug("输入完成")
    }
    def inputValueForNoField(Navigator ctrlDiv,String ...value){
        log.info("向指定字段输入${value}")
        Navigator allInputs = ctrlDiv.$('input').findAll{
            String theType = it.attr('type')
            return theType == ''||theType == null || theType == 'text' || theType=='password'
        }
        allInputs.eachWithIndex{ Navigator entry, int i ->
            forceClearAndSetValue(entry,value[i],true)
        }
    }

    def getValueForField(String fieldName,int index=0){
        log.info("获取页面字段值${fieldName}")
        String value = textInputField(fieldName,index).attr('value')
        log.info("获取到的值为$value")
        return value
    }
    def getValueForField(Navigator ctrlDiv){
        log.info("获取页面字段值${ctrlDiv}")
        String value = textInputFieldByNavigator(ctrlDiv).attr('value')
        log.info("获取到的值为$value")
        return value
    }





}
