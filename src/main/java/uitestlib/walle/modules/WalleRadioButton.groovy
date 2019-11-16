package uitestlib.walle.modules

import geb.module.RadioButtons
import geb.navigator.Navigator
import uitestlib.uicommon.modules.RadioButton

/**
 * Created by QingHuan on 2019/11/17 0:22
 */
class WalleRadioButton  extends RadioButton {
    static content = {
        field { module Field }
    }
    /**
     * 勾选字段中给定名称的单选按钮
     * @param fieldName
     * @param radioName
     * @return
     */

    def checkOnRadioButtonForField(String fieldName, String radioName) {
        checkOnRadioButton(field.controlDiv(fieldName), radioName, 0)
    }

    def checkOnRadioButtonForField(Navigator lbDiv, String radioName) {
        checkOnRadioButton(field.controlDivByNavigator(lbDiv), radioName, 0)
    }

    def checkOnRadioButtonForField(String fieldName, int radioIndex = 0) {
        checkOnRadioButton(field.controlDiv(fieldName), radioIndex)
    }

    def checkOnRadioButtonForField(Navigator lbDiv, int radioIndex = 0) {
        checkOnRadioButton(field.controlDivByNavigator(lbDiv), radioIndex)
    }

    def checkOffRadioButtonForField(String fieldName, String radioName) {
        checkOnRadioButton(field.controlDiv(fieldName), radioName, 0)
    }

    def checkOffRadioButtonForField(String fieldName, int radioName) {
        checkOnRadioButton(field.controlDiv(fieldName), radioName,)
    }

    static boolean isChecked(Navigator chBox) {
        assert chBox, "没有定位到要操作的复选框"
        String ariaChecked = chBox.attr('aria-checked')
        if (ariaChecked != null) {
            return ariaChecked == 'true'
        }
        return chBox.module(RadioButtons).checked

    }


    String getValueForField(String fieldName,Integer fieldIndex=0){
        Navigator ctrlDiv = field.controlDiv(fieldName,fieldIndex)
        Navigator radios = ctrlDiv.$('input',type:'radio')
        for ( Navigator radio in radios) {
            if (isChecked(radio)){
                return radio.parent('span.next-radio').next('span.next-radio-label').text()
            }
        }
        return null
    }

}
