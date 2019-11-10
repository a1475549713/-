package uitestlib.uicommon.modules

import geb.module.RadioButtons
import geb.navigator.Navigator
import groovy.util.logging.Slf4j

/**
 * Created by QingHuan on 2019/11/10 11:37
 */
@Slf4j
class RadioButton extends StableEnhancedMoudle{
    static content = {
        radio { Navigator base,Integer index ->
            Navigator radioButton
            if (base){
                radioButton = base.$('input',type:'radio',index)
            }else{
                radioButton = $('input',type: 'radio',index)
            }
            return radioButton
        }
        radioWithValue { Navigator base,String name,Integer index->
            Navigator radioLabel
            if (base){
                radioLabel = base.$('span.next-radio-lable',text:contains(name))
            }
            else{
                radioLabel = $('span.next-radio-lable',text:contains(name))
            }
            Navigator radioButtonRelated = radioLabel.previous('span.next-radio').$('type',type:'radio',index)
            return radioButtonRelated
        }
    }
    /**
     * 勾选单选框
     * @param base
     * @param index
     * @return
     */

    def checkOnRadioButton(Navigator base = null,Integer index = 0){
        log.info("勾选单选按钮：${base},${index}")
        Navigator rButton = radio(base,index)
        assert rButton,"页面上找不到指定的单选按钮${base},${index}"
        if (!isChecked(rButton)){
            stableClick(rButton)
        }
    }
    /**
     * 勾选单选框
     * @param base
     * @param name
     * @param index
     * @return
     */
    def checkOnRadioButton(Navigator base = null , String name, Integer index = 0){
        log.info("勾选单选按钮：${base},${index}")
        Navigator rButton = radioWithValue(base,name,index)
        assert rButton,"页面上找不到指定的单选按钮${base},${index}"
        if (!isChecked(rButton)){
            stableClick(rButton)
        }
    }

    /**
     * 勾选单选框
     * @param base
     * @param index
     * @return
     */
    def checkOffRadioButton(Navigator base = null,Integer index = 0){
        log.info("勾选单选按钮：${base},${index}")
        Navigator rButton = radio(base,index)
        assert rButton,"页面上找不到指定的单选按钮${base},${index}"
        if (!isChecked(rButton)){
            stableClick(rButton)
        }
    }
    /**
     * 勾选单选框
     * @param base
     * @param name
     * @param index
     * @return
     */

    def checkOffRadioButton(Navigator base = null,String name , Integer index = 0){
        log.info("勾选单选按钮：${base},${index}")
        Navigator rButton = radioWithValue(base,name,index)
        assert rButton,"页面上找不到指定的单选按钮${base},${index}"
        if (!isChecked(rButton)){
            stableClick(rButton)
        }
    }
    /**
     *判断指定的额radioButton是否已被勾选
     * @param rButton
     * @return
     */

    boolean isChecked(Navigator rButton){
        String ariaChecked = rButton.attr('aria-checked')
        if (ariaChecked!=null){
            return ariaChecked =='true'
        }
        return rButton.module(RadioButtons).checked
    }

}
