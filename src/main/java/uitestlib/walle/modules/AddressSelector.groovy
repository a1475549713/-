package uitestlib.walle.modules

import geb.navigator.EmptyNavigator
import geb.navigator.Navigator
import groovy.util.logging.Slf4j

import java.nio.MappedByteBuffer

/**
 * 地址组件建模
 * Created by QingHuan on 2019/11/11 21:04
 */
@Slf4j
class AddressSelector extends Field {
    static content ={
        //全局的地址输入元素
        addressSpanlobal {$('span.next-cn-address')}

        //和某个页面字段关联的地址输入元素
        addressSpanOfGibenField {String labelName ->controlDiv(labelName).$('span.next-cn-address')}

        //和某个页面字段关联的地址输入元素

        addressSpanOfGibenFieldByNavigatot{ Navigator lbDiv ->controlDivByNavigator(lbDiv).$('span.next-cn-address')}

        //国家搜索选择器

        countryListSearchSelect {module( ListSearchSelector)}
    }
    /**
     * 输入地址，按实际情况：国家（如果页面上真有国家选择框的话），省、市、...详细地址
     * @param fieldName
     * @param fieldIndex
     * @param addresses
     * @return
     */
    def inputAddressForField(String fieldName,Integer fieldIndex = 0,String...addresses){
        log.info("设置地址：${fieldName},${fieldIndex},${addresses}")
        Navigator lbDiv = labelDiv(fieldName,fieldIndex)
        assert lbDiv,"页面上找不到名称为$fieldName 的字段"
        inputAddressForField(lbDiv,addresses)
    }

    def inputAddressForField(Navigator lbDiv,String...addresses) {
        log.info("设置地址：${addresses}")
        //获取组件下的全部span元素，一遍判断具体的地址选择元素类型
        String addressType = 'normal'
        Navigator allSpans = controlDivByNavigator(lbDiv).$('span')
        for (Navigator span in allSpans) {
            String spanCls = span.attr('class')
            if (spanCls.contains('next-cn-address-multi-wrap')) {
                addressType = 'multi'
                break
            }
        }
        if (addressType == 'normal') {
            //如果是span.next-cn-address 类型的地址组件
            Navigator addressSpan = addressSpanOfGibenFieldByNavigatot(lbDiv)
            //判断地址选择元素中是否包含国家
            Navigator children = addressSpan.children('span')
            Navigator country = new EmptyNavigator()
            Navigator domestic = new EmptyNavigator()
            for (Navigator child in children) {
                String spanClass = child.attr('class')
                if (spanClass.contains('next-cn-address-country')) {
                    country = child
                }
                if (spanClass.contains('next-cn-address-domestic')) {
                    domestic = child
                }
            }
            List<String> allAddress = addresses as List
            if (country) {
                String countryName = allAddress.pop()
                log.info("地址设置元素中包含国家字段，从参数中获取到的值为$countryName")
                countryListSearchSelect.searchAndSelectValueForField(lbDiv, countryName, 0)
                sleep(1000)
            }
            if (domestic) {
                Navigator trigger = domestic.$('i.next-icon', class: contains('next-icon-arrow-'), 0)
                triggerUntilOpened(trigger)
                sleep(1000)

                //已成功点击过的地址
                List<String> clicked = []
                for (String addr in allAddress) {
                    boolean success = findAndClickAddressButton(addr)
                    if (success) {
                        log.info("设置地址字段:$addr")
                        clicked.add(addr)
                        sleep(1000)
                    } else {
                        break
                    }
                }
                //剩下的全部当成详细地址字段
                allAddress.removeAll(clicked)
                if (allAddress) {
                    log.info("获取到详细地址字段的值为:$allAddress")
                    Navigator iDiv = controlDivByNavigator(lbDiv)
                    Navigator inputElement = new EmptyNavigator()
                    List<Navigator> allChildren = getChildrenRecursively(iDiv)
                    for (Navigator child in allChildren) {
                        if (child.tag() == "input" && child.attr('type') == 'text') {
                            inputElement = child
                        }
                    }
                    if (inputElement) {
                        allAddress.each {
                            inputElement << it
                        }
                    } else {
                        log.info("入参中有箱子地址字段，但是页面上没有详细地址输入框")
                    }
                }
            }
        }
        else {
            //是span.next-address-multi-wrapp类型的地址组件
            Navigator addressSpan = controlDivByNavigator(lbDiv).$('span.next-cn-address-multi-wrapp')
            Navigator trigger = addressSpan.$('i.next-icon',class:contains("next-icon-arrow-"),0)
            triggerUntilOverlayOpened(trigger)
            sleep(1000)
            //已经成功点击过的地址
            List<String> allAddress = addresses as List
            List<String> clicked = []
            for (String addr in allAddress) {
                boolean success = findAndClickAddressButton(addr)
                if (success) {
                    log.info("设置地址字段:$addr")
                    clicked.add(addr)
                    sleep(1000)
                } else {
                    break
                }
            }
            //剩下的全部当成详细地址字段
            allAddress.removeAll(clicked)
            if (allAddress) {
                log.info("获取到详细地址字段的值为:$allAddress")
                Navigator iDiv = controlDivByNavigator(lbDiv)
                Navigator inputElement = new EmptyNavigator()
                List<Navigator> allChildren = getChildrenRecursively(iDiv)
                for (Navigator child in allChildren) {
                    if (child.tag() == "input" && child.attr('type') == 'text') {
                        inputElement = child
                    }
                }
                if (inputElement) {
                    allAddress.each {
                        inputElement << it
                    }
                } else {
                    log.info("入参中有箱子地址字段，但是页面上没有详细地址输入框")
                }
            }
        }
    }


    def inputAddressForNonField(Navigator ctrlDiv,String ...addresses){
        log.info("设置地址：$addresses")
        String addressType = 'normal'
        Navigator allSpans = ctrlDiv.$('span')
        for (Navigator span in allSpans) {
            String spanCls = span.attr('class')
            if (spanCls.contains('next-cn-address-multi-wrap')) {
                addressType = 'multi'
                break
            }
        }
        if (addressType == 'normal') {
            //如果是span.next-cn-address 类型的地址组件
            Navigator addressSpan = ctrlDiv.$('span.next-cn-address')
            //判断地址选择元素中是否包含国家
            Navigator children = addressSpan.children('span')
            Navigator country = new EmptyNavigator()
            Navigator domestic = new EmptyNavigator()
            for (Navigator child in children) {
                String spanClass = child.attr('class')
                if (spanClass.contains('next-cn-address-country')) {
                    country = child
                }
                if (spanClass.contains('next-cn-address-domestic')) {
                    domestic = child
                }
            }
            List<String> allAddress = addresses as List
            if (country) {
                String countryName = allAddress.pop()
                log.info("地址设置元素中包含国家字段，从参数中获取到的值为$countryName")
                countryListSearchSelect.searchAndSelectValueForField(ctrlDiv, countryName, 0)
                sleep(1000)
            }
            if (domestic) {
                Navigator trigger = domestic.$('i.next-icon', class: contains('next-icon-arrow-'), 0)
                triggerUntilOpened(trigger)
                sleep(1000)

                //已成功点击过的地址
                List<String> clicked = []
                for (String addr in allAddress) {
                    boolean success = findAndClickAddressButton(addr)
                    if (success) {
                        log.info("设置地址字段:$addr")
                        clicked.add(addr)
                        sleep(1000)
                    } else {
                        break
                    }
                }
                //剩下的全部当成详细地址字段
                allAddress.removeAll(clicked)
                if (allAddress) {
                    log.info("获取到详细地址字段的值为:$allAddress")

                    Navigator iDiv = ctrlDiv
                    Navigator inputElement = new EmptyNavigator()
                    List<Navigator> allChildren = getChildrenRecursively(iDiv)
                    for (Navigator child in allChildren) {
                        if (child.tag() == "input" && child.attr('type') == 'text') {
                            inputElement = child
                        }
                    }
                    if (inputElement) {
                        allAddress.each {
                            inputElement << it
                        }
                    } else {
                        log.info("入参中有箱子地址字段，但是页面上没有详细地址输入框")
                    }
                }
            }
        }
        else {
            //是span.next-address-multi-wrapp类型的地址组件
            Navigator addressSpan = ctrlDiv.$('span.next-cn-address-multi-wrapp')
            Navigator trigger = addressSpan.$('i.next-icon',class:contains("next-icon-arrow-"),0)
            triggerUntilOverlayOpened(trigger)
            sleep(1000)
            //已经成功点击过的地址
            List<String> allAddress = addresses as List
            List<String> clicked = []
            for (String addr in allAddress) {
                boolean success = findAndClickAddressButton(addr)
                if (success) {
                    log.info("设置地址字段:$addr")
                    clicked.add(addr)
                    sleep(1000)
                } else {
                    break
                }
            }
            //剩下的全部当成详细地址字段
            allAddress.removeAll(clicked)
            if (allAddress) {
                log.info("获取到详细地址字段的值为:$allAddress")
                Navigator iDiv = ctrlDiv
                Navigator inputElement = new EmptyNavigator()
                List<Navigator> allChildren = getChildrenRecursively(iDiv)
                for (Navigator child in allChildren) {
                    if (child.tag() == "input" && child.attr('type') == 'text') {
                        inputElement = child
                    }
                }
                if (inputElement) {
                    allAddress.each {
                        inputElement << it
                    }
                } else {
                    log.info("入参中有箱子地址字段，但是页面上没有详细地址输入框")
                }
            }
        }

    }

    /**
     * 查找并点击对应的地址按钮，返回值为true就表示点击到了，返回值为false表示没点击到，则该地址后面地址都当做详细地址
     * @param addressName
     * @param iaMulti
     * @return
     */

    private boolean findAndClickAddressButton(String addressName ,boolean  iaMulti = false){
        Navigator addressButtons = iaMulti ?$('div.cn-addr-casitem'):$("button.next-cn-address-label")
        boolean success = false
        for (Navigator addr in addressButtons){
            if (iaMulti){
                Navigator textSpan = addr.$('span.cn-addr-castext')
                if (textSpan.text().contains(addressName)){
                    Navigator checkbox = textSpan.siblings('input',type:'checkbox')
                    assert checkbox,"没有找到级联地址前面的复选框"
                    stableClick(checkbox)
                    success =true
                    break
                }
            }
            else {
                if (addr.text().contains(addressName)){
                    stableClick(addr)
                    success = true
                    break
                }
            }
        }
        return success
    }

    /**
     * 获取给定字段值的地址元素
     * @param fieldName
     * @param fieldIndex
     * @return
     */
    List<String> getAddressForField(String fieldName,Integer fieldIndex = 0){
        log.info("获取给定页面字段的：$fieldName,$fieldIndex")
        Navigator theFiled = controlDiv(fieldName,fieldIndex)
        assert theFiled,"页面上找不到名称为$fieldName,$fieldIndex 的地址类型元素"
        getAddressForField(theFiled)
    }


    List<String> getAddressForField(Navigator ctrlDiv) {
        List<String> resultList = []
        Navigator addressSpanDiv = ctrlDiv.$('span.next-cn-address')
        Navigator country = addressSpanDiv.$('span.next-cn-address-country')
        if (country){
            String text = country.$('span.next-select-inner',0).text()
            resultList.add(text)
        }
        Navigator domestic = addressSpanDiv.$('span.next-cn-address-domestic')
        if (domestic){
            String domesticText = domestic.$('span.next-select-inner',0).text()
            resultList.addAll(domesticText.split('/'))
        }
        Navigator detailAddress = ctrlDiv.$('input',placeholder:'请输入详细地址',0)
        if (detailAddress){
            resultList.add(detailAddress.attr('value'))
        }
        return resultList
    }

    def inputAddressOnOpenedOverlay(String...address){
        log.info("在已经打开的图层中输入地址：只支持输入中国的省，市。区等:$address")
        Navigator domestic = waitFor {$'span.next-cn-address-domestic'}
        Navigator trigger = waitFor {domestic.$('i.next-icon.next-arrow-down',0)}
        triggerUntilOpened(trigger)
        sleep(1000)
        //点击到省所在的tab
        overlay.$('div.next-tabs-tab-inner',text:"省").click()
        //已经成功点击过的地址
        List<String> clicked = []
        for (String addr in address){
            boolean success = findAndClickAddressButton(addr)
            if (success){
                log.info("设置地址字段:${addr}")
                clicked.add(addr)
                sleep(1000)
            }
            else{
                break
            }
        }


    }


}
