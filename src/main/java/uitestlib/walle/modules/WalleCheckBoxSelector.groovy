package uitestlib.walle.modules

import geb.navigator.Navigator
import uitestlib.uicommon.modules.CheckBoxSelector

/**
 * 建模复选框元素
 * Created by QingHuan on 2019/11/16 19:08
 */
class WalleCheckBoxSelector extends CheckBoxSelector{
    static content ={
         field {module(Field)}
    }

    /**
     * 勾选字段中给定名称放入复选框
     * @param fieldName
     * @param checkboxNmaes
     * @return
     */
    def checkOnCheckBoxForField(String fieldName,String...checkboxNmaes){
        checkboxNmaes.each {
            checkOnCheckBox(field.controlDiv(fieldName),it,0)
        }
    }

    /**
     * 勾选字段中给定名称的复选框
     * @param lbDiv
     * @param checkboxNames
     * @return
     */
    def checkOnCheckBoxForField(Navigator lbDiv,String...checkboxNames){
        checkboxNames.each {
            checkOnCheckBox(field.controlDivByNavigator(lbDiv),it,0)
        }
    }
    /**
     *
     * @param fieldName
     * @param checkboxNames
     * @return
     */
    def checkOffCheckBoxForField(String fieldName,String...checkboxNames){
        checkboxNames.each {
            checkOffCheckBox(field.controlDiv(fieldName),it,0)
        }
    }
    /**
     * 勾选字段中给定索引的复选框
     * @param fielName
     * @param radioIndexes
     * @return
     */
    def checkOffCheckBoxForField(String fielName,Integer...radioIndexes) {
        radioIndexes.each {
            checkOffCheckBox(field.controlDiv(fieldName), it)

        }
    }
    /**
     * 返回给定控件中的 checkbox 的值
     * @param fieldName
     * @param fieldIndex
     * @return
     */
    List<String> getValueForField(String fieldName,Integer fieldIndex=0){
        Set<String> resultList=[]
        //查找该字段中所有的复选框，返回被选中的各个复选框的列表
        Navigator ctrDiv=field.controlDiv(fieldName,fieldIndex)
        Navigator checkBoxes = ctrDiv.$('input',type:'checkbox')
        for (Navigator c  in checkBoxes ) {
            if (isChecked(c)){
                resultList.add(c.attr('value'))
            }
        }
        return  resultList as List<String>

        }






}
