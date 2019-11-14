package uitestlib.walle.modules

import groovy.util.logging.Slf4j
import uitestlib.uicommon.modules.StableEnhancedMoudle
import geb.navigator.Navigator

/**
 * @TODO 所有输入字段抽象，具体输入字段需要子类实现
 *
 * Created by QingHuan on 2019/11/11 21:07
 */
@Slf4j
class Field  extends StableEnhancedMoudle{

    static ocntent = {
        //页面上所有含label的元素，不含label的元素，需要在各自的Page页面中做处理，不考虑特殊场景
        allLabels { $('label') }
        allLabelDivs { allLabels.parent('div') }
        labelDiv { String labelName,int index = 0 ->
            /**
             * @TODO 查找元素默认10秒隐士等待，GebConfig.groovy中配置
             * 这里的？.contains很重要，判断元素存在后再执行contains方法，不加？有可能会报错
             */
            if(index == 0 ){
                Navigator niv = elementsToNavigator('label').find{it.text()?.contains(labelName)}
                assert niv
                return niv.parent('div')
            }
            Navigator niv = elementsToNavigator('label').findAll {it.text()?.contains(labelName)}
            assert niv
            return niv[index].parent('div')
        }
        //和上面名称相关联的输入单元格
        controlDiv {String labelName ,int index = 0->
            Navigator lbDiv = labelDiv(labelName,index)
            return controlDivByNavigator(lbDiv)
        }
        //直接使用Navigator 类型的labelCell定位出来的inputCell,这种使用前提是已经使用labelCell（labelName）得到了具体的导航器元素
        controlDivByNavigator {Navigator lbDiv ->
            Navigator lbRelatedControl = lbDiv.$('div.next-form-item-control')
            if (lbRelatedControl){
                return lbRelatedControl
            }
            //控件在<label的父div元素的兄弟元素中，既lbDiv的兄弟元素
            lbRelatedControl = lbDiv.next('div.next-form-item-control')
            if (lbRelatedControl){
                return lbRelatedControl
            }
            //控件在<labe 的父Div元素的兄弟元素的子元素中，既lbDiv的兄弟div的子元素中
            lbRelatedControl = lbDiv.next('div').$('div.next-form-item-control')
            if (lbRelatedControl){
                return lbRelatedControl
            }
            return lbRelatedControl
        }

        //弹出层处理，适用于所有点击后会弹出页面的元素。如列表选择，列表搜索，日期选择元素等，每次检查2S没找到就默认没有
        // 使用 -1来索引最新的overlay
        overlay(wait:2){$('div.next-overlay-wrapper.opened',-1)}
    }

    /**
     * 动态猜测页面元素的类型，期间会设计点击下拉框等操作，等交互完成后才能判断出元素类型
     * @param ctrlDiv
     * @return
     */
    String guessElementTypeOnTheFly(Navigator ctrlDiv){
        boolean hasTrigger = false

        /**
         * 是否可能是日期或日期范围元素
         */

        boolean potentialCalendarPart1 = false
        boolean potentialCalendarPart2 = false

        List<Navigator> children =  getChildrenRecursively(ctrlDiv)
        for (Navigator child in children) {
            if (child.tag() =='i'){
                String childClass =child.attr('class')
                if (childClass.contains('next-icon-arrow-')){
                    hasTrigger = true
                }
                if (childClass.contains("next-icon-calendar")){
                    potentialCalendarPart1 = true
                }
            }
            else if (child.tag() =='span'){
                String childClass = child.attr('class')
                if (childClass.contains("next-cn-address")||childClass.contains("next-cn-address-domestic")||childClass.contains("next-cn-address-country")){
                    return 'AddressSelector'
                }
                else if (childClass.contains("next-time-picker")){
                    return 'TimeInput'
                }
                else if (childClass.contains("next-checkbox")){
                    return 'CheckBox'
                }
                else if (childClass.contains("next-radio")){
                    return 'WalleRadioButton'
                }
                else if (childClass.contains("next-number-picker")){
                    return 'TextInput'
                }
            }
            else if (child.tag() == 'div'){
                String childClass = child.attr('class')
                if (childClass.contains('next-date-picker')){
                    if (childClass.contains("next-range-picker")){
                        return 'DateRangeInput'
                    }
                    return 'DataInput'
                }
                else if (childClass.contains('staff')){
                    return 'BucUserSelector'
                }
                else if (childClass.contains('z-address-group')){
                    return 'ZAddressGroup'
                }
                else if (childClass.contains('z-tag-group')){
                    return 'ZTagGroup'
                }
                else if (childClass.contains("next-range-picker")){
                    potentialCalendarPart2 = true
                }
            }
        }

        if (potentialCalendarPart1 && potentialCalendarPart2){
            return 'DateRangeInput'
        }
        if (hasTrigger){
            return 'ListSearchSelector'
        }
        else {
            for (Navigator child in children) {
                if (child.tag() == 'textarea'){
                    return 'TextAreaInput'
                }
                if (child.tag() == 'input'){
                    return 'TextInput'
                }
            }
        }
        return 'UnKnown'
    }

    /**
     * 动态猜测页面类型
     * @param fieldName 字段名称
     * @param fieldIndex 下标0 123
     * @return
     */
    String guessElementTypeOnTheFly(String fieldName,Integer fieldIndex=0){
        Navigator niv = labelDiv(fieldName,fieldIndex)
        assert niv,"页面上没有指定名称和索引的字段$fieldName,$fieldIndex"
        Navigator relatedControlDiv = controlDivByNavigator(niv)
        assert relatedControlDiv,"根据页面字段名称及缩影，没有找到与其相关联的空间"
        guessElementTypeOnTheFly(relatedControlDiv)
    }

    def triggerUntilOverlayOpened(Navigator trigger){
        for (int i in 0..3){
            if (trigger.attr('class').contains('next-icon-delete-filling')){
                stableClick(trigger)
                stableClick(trigger)
            }
            else {
                stableClick(trigger)
            }
            if (overlay){
                break
            }
        }

    }


}
