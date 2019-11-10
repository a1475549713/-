package uitestlib.uicommon.modules

import geb.navigator.Navigator

/**
 * Created by QingHuan on 2019/11/10 12:03
 */
@SuppressWarnings('unused')
class NamedDiv extends StableEnhancedMoudle{
    static content ={
        div {String name -> $('div',text:name,0)}
    }

    /**
     * 点击给定文本的第一个div元素
     * @param divText
     */
    def clickNamedDiv(String divText){
        Navigator theDiv = div(divText)
        assert theDiv,"页面上找不到文本为${divText},的div元素"
        stableClick(theDiv)
    }
}
