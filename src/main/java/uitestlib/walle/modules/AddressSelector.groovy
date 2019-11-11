package uitestlib.walle.modules

import geb.navigator.Navigator
import groovy.util.logging.Slf4j

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

}
