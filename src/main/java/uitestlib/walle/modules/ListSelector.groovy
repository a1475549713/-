package uitestlib.walle.modules

import geb.navigator.EmptyNavigator
import geb.navigator.Navigator
import groovy.util.logging.Slf4j

/**
 * Created by QingHuan on 2019/11/14 23:09
 */
@Slf4j
class ListSelector  extends Field{
    static content = {
        selectedItem {String textValue ->
            Navigator allPossible = overlay.$('li')
            def reverse = allPossible.iterator().reverse()
            for (Navigator item : reverse) {
                if (item.text() == textValue){
                    log.debug("精确找到文本为${textValue}，的选项")
                    return item
                }
            }

            /**
             * @TODO 重置迭代器！！
             */
            reverse =  allPossible.iterator().reverse()
            for (Navigator item : reverse) {
                if (item.text() .contains(textValue) ){
                    log.debug("精确找到文本包含${textValue}，的选项")
                    return item
                }
            }
            return new EmptyNavigator()
        }
    }


    /**
     * 为指定字段选择指定的值
     * @param fieldName
     * @param value
     * @return
     */
    def selectValueForField (String fieldName,String value){
        log.info("为页面字段选择值${fieldName},${value}")
        Navigator lbDiv = labelDiv(fieldName)
        assert lbDiv,"页面上没有找到名称为：${fieldName},的字段"
        selectValueForField(lbDiv,value)
    }

    def selectValueForField(Navigator lbDiv,String value){
        selectValueForField(lbDiv,value,0)
    }

    def selectValueForField(String fieldName,String...value){
        log.info("为页面字段选择值${fieldName},${value}")
        Navigator lbDiv = labelDiv(fieldName)
        assert lbDiv,"页面上没有找到名称为：${fieldName},的字段"
        selectValueForField(lbDiv,value)
    }

    def selectValueForField(Navigator lbDiv,String...value){
        assert lbDiv
        log.info("为页面字段选择值,${value}")
        value.eachWithIndex {String entry,int i ->
            log.info("处理选项${entry}")
            selectValueForField(lbDiv,entry,i)
        }
    }


    def selectValueForField(String fieldName,String value,int iconIndex){
        log.info("为页面字段选择值${fieldName},${value},${iconIndex}")
        Navigator lbDiv = labelDiv(fieldName)
        assert lbDiv,"页面上没有找到名称为：${fieldName},的字段"
        selectValueForField(lbDiv,value,iconIndex)
    }

    def selectValueForField(Navigator lbDiv,String value,int iconIndex){
        log.info("为页面字段选择值${value},${iconIndex}")
        //字段输入框右侧的下拉框
        Navigator trigger = controlDivByNavigator(lbDiv).$('i.next-icon',class:contains('next-icon-arrow-'),iconIndex)
        if (!trigger){

                /**
                 * 2:处理，下一个选择框应该不属于当前的controlDiv，这里做一个兼容，在接着的一个或多个controlDiv里面找
                 */
                Navigator lbDivParent = lbDiv.parent('div')
                iconIndex.times {
                    lbDivParent = lbDivParent.next('div')
                }
                trigger= lbDivParent.$('i.next-icon',class:contains('next-icon-arrow-'))

        }
        if (trigger?.attr('class')?.contains('next-icon-arrow-down')){
            //如果按钮是向下的，就点击张开，否则证明已经展开的了，就不用点击
            triggerUntilOpened(trigger)
        }
        stableClick(selectedItem(value))
        log.debug("设置成功")
    }


    def selectValueForNoField(Navigator ctrlDiv,String...value){
        value.eachWithIndex{ String entry, int i ->
            selectValueForNoField(ctrlDiv,entry,i)
        }
    }

    def selectValueForNoField(Navigator ctrlDiv,String value,int iconIndex){
        log.info("为没有标签的字段选择给定的值${ctrlDiv},${value},${iconIndex}")
        //字段输入框右侧下拉按钮
        Navigator trigger = ctrlDiv.$('i.next-icon',class:contains('next-icon-arrow-'),iconIndex)

        if (trigger?.attr('class')?.contains('next-icon-arrow-down')){
            //如果按钮是向下的，就点击张开，否则证明已经展开的了，就不用点击
            triggerUntilOpened(trigger)
        }
        stableClick(selectedItem(value))
        log.debug("设置成功")
    }

    /**
     * 获取给定页面字段的值
     * @param fieldName
     * @param fieldIndex
     * @return
     */
    List<String> getValueForField(String fieldName,Integer fieldIndex=0){
        log.info("获取页面给定字段值：${fieldName},${fieldIndex}")
        Navigator theDiv = labelDiv(fieldName,fieldIndex)
        assert theDiv,"页面上没有找到名称为${fieldName}，的字段"
        getValueForField(theDiv)
    }

    List<String> getValueForField(Navigator lbDiv){
        log.info("获取页面给定字段的值")
        Set<String> resultList = []
        controlDivByNavigator(lbDiv).$('span.next-select-inner').each{
            resultList.add(it.text())
        }
        log.info("获取到的值为：${resultList.toString()}")
        return resultList as List<String>
    }

    /**
     * 在已经打开的图层中搜索和选择元素
     * @param value
     * @return
     */

    def selectValueOnOpenedOver(String value){
        log.info("在已经打开的图层中搜索和选择元素:${value}")
        stableClick(selectedItem(value))
        log.debug("设置成功")
    }

}
