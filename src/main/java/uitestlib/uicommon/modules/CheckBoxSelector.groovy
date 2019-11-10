package uitestlib.uicommon.modules

import geb.module.Checkbox
import geb.navigator.Navigator
import javafx.scene.control.CheckBox

/**
 * Created by QingHuan on 2019/11/10 12:38
 */
class CheckBoxSelector extends StableEnhancedMoudle{
    static content = {
        cbox { Navigator base = null,Integer index ->
            Navigator checkBox
            if (base){
                checkBox = base.$('input',type:'checkbox',index)
                /**
                 * 处理特例，type不是;'checkbox'的情况
                 */
                if(!checkBox){
                    checkBox = base.$('label.c-checkbox',index)
                }
            }
            else {
                checkBox = $('input',type:'checkbox',index)
                if(!checkBox){
                    checkBox = $('label.c-checkbox',index)
                }
            }
            return checkBox
        }
        checkWithValue {Navigator base =null,String name ,Integer index->
            Navigator checkBox
            if (base){
                Navigator checkboxLabel = base.$('span-next-checkbox-label',text:contains(name))
                if (checkboxLabel){
                    checkBox  = checkboxLabel.previous('span.next-checkbox').$('input',type: 'checkbox',index)
                }
                else {
                    checkBox = base.$('input',type:'checkbox',value:contains(name),index)
                }
                /**
                 * 上面没找到，考虑是否在 c-checkbox
                 */
                if (!checkBox){
                    Navigator outerDiv = $('div.c-checkbox-field',text: contains(name),index)
                    checkBox = outerDiv.$('label.c-checkbox')
                }
                /**
                 * 上面还是没找到，考虑这个text是否在其父div或祖先div文本里
                 */
                if (!checkBox){
                    Navigator c = $('label.c-checkbox')
                    Navigator w = $('input[type="checkbox"]')
                    Navigator ckbox = c?c:w

                    if (ckbox){
                       List<Navigator> ckboxList = []
                        for (Navigator cb : ckbox) {
                            if (isAncestorDivsContainsText(cb,name,5)){
                                ckboxList.add(cb)
                            }
                        }
                        if (ckboxList.size() >index){
                            return ckboxList[index]
                        }
                    }
                }
            }
            else{
                //有两种checkbox，一种有label
                Navigator checkboxLabel = $('span.next-checkbox-label',text: contains(name))
                if (checkboxLabel){
                    checkBox = checkboxLabel.previous('span.next-checkbox').$('input',type:'checkbox',index)
                }
                else{
                    checkBox = $('input',type:'checkbox',value: contains(name),index)
                }
                if (!checkBox){
                    Navigator outerDiv = $('div.c-checkbox-field').parent('div',text: contains(name))[index]
                    checkBox = outerDiv.$('label.c-checkbox')
                }

                if (!checkBox){
                    Navigator c = $('label.c-checkbox')
                    Navigator w = $('input[type="checkbox"]')
                    Navigator ckbox = c?c:w
                    if (ckbox){
                        List<Navigator> ckboxList = []
                        for (Navigator cb : ckbox) {
                            if (isAncestorDivsContainsText(cb,name,5)){
                                ckboxList.add(cb)
                            }
                        }
                        if (ckboxList.size() >index){
                            return ckboxList[index]
                        }
                    }
                }
            }
            return checkBox
        }
    }


    def checkOnCheckBox(Navigator base = null,Integer index = 0){
        log.debug("勾选复选框：${base},${index}")
        Navigator checkBox = cbox(base,index)
        assert checkBox,"页面上找不到指定的复选框：base:${base},index:${index}"
        if (!isChecked(checkBox)){
            stableClick(checkBox)
        }
    }
    def checkOnCheckBox(Navigator base = null,String name,Integer index =0){
        log.info("勾选复选框：$base,$name,$index")
        Navigator checkBox =checkWithValue(base,name,index)
        assert checkBox,"页面上找不到指定的复选框：name:$name, base:${base},index:${index}"
        if (!isChecked(checkBox)){
            stableClick(checkBox)
        }
    }

    def checkOffCheckBox(Navigator base = null,Integer index = 0){
        log.info("勾选复选框：$base,$index")
        Navigator checkBox = cbox(base,index)
        assert checkBox,"页面上找不到指定的复选框： base:${base},index:${index}"
        if (isChecked(checkBox)){
            stableClick(checkBox)
        }
    }

    def checkOffCheckBox(Navigator base =null , String name,Integer index = 0){
        log.info("勾选复选框：$name,$index")
        Navigator checkBox =checkWithValue(base,name,index)
        assert checkBox,"页面上找不到指定的复选框：name:$name, base:${base},index:${index}"
        if (!isChecked(checkBox)){
            stableClick(checkBox)
        }
    }


    /**
     * 判断给定的checkbox是否已被勾选
     * @param chBox
     * @return
     */
    static boolean isChecked(Navigator chBox){
        assert chBox,"没有定位到要操作的复选框"
        String ariaChecked = chBox.attr('aria-checked')
        if (ariaChecked !=null){
            return ariaChecked == 'true'
        }

        //是否是其他页面
        Boolean selected = chBox.attr('class')?.contains('selected')
        if (selected){
            return true
        }
        //当做普通checkbox
        return chBox.module(Checkbox).checked
    }


}
