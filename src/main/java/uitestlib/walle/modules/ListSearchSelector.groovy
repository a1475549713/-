package uitestlib.walle.modules

import geb.navigator.EmptyNavigator
import geb.navigator.Navigator
import groovy.util.logging.Slf4j

/**
 * 下来列表类型输入框，点击下拉按钮后，会展示可选的选项
 * Created by QingHuan on 2019/11/11 22:18
 */
@Slf4j
class ListSearchSelector extends Field{

    static content = {
        //待选择的内容
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
        //待选择的级联元素
        selectedCascaderItem {Navigator base,String textValue ->
            base.$('li',text:textValue)
        }
    }
    /**
     * 为指定字段选择指定的值
     * @param fieldName
     * @param value
     * @return
     */
    def searchAndSelectValueForField (String fieldName,String value){
        log.info("为页面字段选择值$fieldName,$value")
        Navigator lbDiv = labelDiv(fieldName)
        assert lbDiv,"页面上没有找到名称为：$fieldName,的字段"
        searchAndSelectValueForField(lbDiv,value)
    }

    def searchAndSelectValueForField(Navigator lbDiv,String value){
        searchAndSelectValueForField(lbDiv,value,0)
    }

    def searchAndSelectValueForField(String fieldName,String...value){
        log.info("为页面字段选择值$fieldName,$value")
        Navigator lbDiv = labelDiv(fieldName)
        assert lbDiv,"页面上没有找到名称为：$fieldName,的字段"
        searchAndSelectValueForField(lbDiv,value)
    }

    def searchAndSelectValueForField(Navigator lbDiv,String...value){
        assert lbDiv
        log.info("为页面字段选择值,$value")
        value.eachWithIndex {String entry,int i ->
            log.info("处理选项$entry")
            searchAndSelectValueForField(lbDiv,entry,i)
        }
    }

    def searchAndSelectValueForField(String fieldName,String value,int iconIndex){
        log.info("为页面字段选择值$fieldName,$value,$iconIndex")
        Navigator lbDiv = labelDiv(fieldName)
        assert lbDiv,"页面上没有找到名称为：$fieldName,的字段"
        searchAndSelectValueForField(lbDiv,value,iconIndex)
    }


    def searchAndSelectValueForField(Navigator lbDiv,String value,int iconIndex){
        log.info("为页面字段选择值e,$value,$iconIndex")
        //字段输入框右侧的下拉框
        Navigator trigger = controlDivByNavigator(lbDiv).$('i.next-icon',class:contains('next-icon-arrow-'),iconIndex)
        if (!trigger){
            /**
             * 1:处理，先看看当前元素是否在一个下拉框的级联选择组件，没有下拉按钮，但是又被判断Wie选择组件，此时 overlay肯定打开了
             */
            Navigator cascader = overlay.$('div.next-cascader')
            if (cascader){
                log.info("是同一个下拉框中的级联选择组件")
            }
            else{
                /**
                 * 2:处理，下一个选择框应该不属于当前的controlDiv，这里做一个兼容，在接着的一个或多个controlDiv里面找
                 */
                Navigator lbDivParent = lbDiv.parent('div')
                iconIndex.times {
                    lbDivParent = lbDivParent.next('div')
                }
                trigger= lbDivParent.$('i.next-icon',class:contains('next-icon-arrow-'))
            }
        }
        if (trigger?.attr('class')?.contains('next-icon-arrow-down')){
            //如果按钮是向下的，就点击张开，否则证明已经展开的了，就不用点击
            triggerUntilOpened(trigger)
        }
        Navigator cascader = overlay.${'div.next-cascader'}
        if (cascader){
            Navigator base = waitFor {overlay.$('div.next-cascader-menu-wrapper',iconIndex)}
            stableClick(selectedCascaderItem(base,value))
        }
        else{
            doSearchWhenNneeded(value)
            chooseValue(value)
        }
        log.debug("设置成功")
    }

    def searchAndSelectValueForNonField(Navigator ctrlDiv,String...value){
        value.eachWithIndex{ String entry, int i ->
            searchAndSelectValueForNonField(ctrlDiv,entry,i)
        }
    }

    def searchAndSelectValueForNonField(Navigator ctrlDiv,String value,int iconIndex){
        log.info("为没有标签的字段选择给定的值$ctrlDiv,$value,$iconIndex")
        //字段输入框右侧下拉按钮
        Navigator trigger = ctrlDiv.$('i.next-icon',class:contains('next-icon-arrow-'),iconIndex)

        if (trigger?.attr('class')?.contains('next-icon-arrow-down')){
            //如果按钮是向下的，就点击张开，否则证明已经展开的了，就不用点击
            triggerUntilOpened(trigger)
        }
        doSearchWhenNeeded(value)
        //选择给定的选项
        chooseValue(value)
        log.debug("设置成功")
    }


    /**
     * 获取给定页面字段的值
     * @param fieldName
     * @param fieldIndex
     * @return
     */
    List<String> getValueForField(String fieldName,Integer fieldIndex=0){
        log.info("获取页面给定字段值：$fieldName,$fieldIndex")
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
        //判断获取到的是是不是同一个下拉框中的级联选择器的值，如果是的话，就把值拆分
        if (resultList.size() ==1){
            if (resultList[0].contains(" /")){
                log.info("该组件可能是个级联选择器，对结果进行拆分")
                List<String> splited = resultList[0].split(" / ")
                resultList.clear()
                resultList.addAll(splited)
            }
        }
        log.info("获取到的值为：${resultList.toString()}")
        return resultList as List<String>
    }
    /**
     * 在已经打开的图层中搜索和选择元素
     * @param value
     * @return
     */

    def searchAndSelectValueOnOpenedOver(String value){
        log.info("在已经打开的图层中搜索和选择元素:$value")
        doSearchWhenNeeded(value)
        chooseValue(value)
        log.debug("设置成功")
    }


    def doSearchWhenNeeded(String value){
        //检查是否有搜索框，如果有就执行搜索，没有就跳过
        //这样在ListSearchSelector 中就可以兼容ListSelector的逻辑，在猜测元素类型是，就不用点开弹出层
        Navigator searchDiv = overlay.$('div.next-menu-header')
        if (searchDiv){
            searchDiv.$('input',type:'text')<<value
            stableClick(searchDiv.$('i.next-icon-search'))
        }
    }

    /**
     * 选择值
     * @param value
     * @return
     */
    def chooseValue(String value){
        Navigator item = selectedItem(value)
        assert item,"页面上找不到值为$value,的下拉选项"
        assert item.size() ==1,"页面上找不到不止一个值为$value,的下拉选项，现在个数为${item.size()}"
        stableClick(item)
    }

}
