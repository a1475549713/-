package uitestlib.walle.modules

import geb.navigator.Navigator

import javax.swing.JLabel

/**
 * Created by QingHuan on 2019/11/16 18:12
 */
class TextAreaInput extends Field{
    static content = {
        textAreaField {String labeName ,int index = 0 -> controlDiv(labeName,index).$('textarea')}

        textAreaFieldByNavigator { Navigator lbDiv -> controlDivByNavigator(lbDiv).$('textarea')}
    }

    def inputValueForTextAreaField (String fieldName,String value,int index=0){

        log.info("向页面输入值：${fieldName},${index},${value}")
        Navigator lvDiv = labelDiv(fieldName,index)
        assert lvDiv,"找不到 ${fieldName}字段"
        inputValueForTextAreaField(lvDiv,value)
    }


    def inputValueForTextAreaField (Navigator lbDiv,String value){
        log.info("向页面输入值：${value}")
        Navigator niv = textAreaFieldByNavigator(lbDiv)
        forceClearAndSetValue(niv,value)
    }

    def inputValueForTextAreaField (Navigator lbDiv,String... value){
        log.info("向页面输入值：${value}")
        Navigator allInputs = lbDiv.$('textarea')
        allInputs.eachWithIndex{ Navigator entry, int i ->
            forceClearAndSetValue(entry,value[i])
        }
    }


    def getValueForField(String fieldName,int index=0){
        log.info("获取页面字段值${fieldName}")
        String value = textAreaField(fieldName,index).text()
        log.info("获取到的值为$value")
        return value
    }


}
