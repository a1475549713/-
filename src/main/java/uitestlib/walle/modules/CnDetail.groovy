package uitestlib.walle.modules

import geb.navigator.Navigator
import uitestlib.uicommon.modules.StableEnhancedMoudle

/**
 * Created by QingHuan on 2019/11/13 21:47
 */
class CnDetail extends StableEnhancedMoudle{
    static content = {
        /**
         * uniqueItemName 用来区分具体想要操作的是哪个CnDetail，因为页面上可能有多个，这个时候需要根据里面具体的内容区分一下
         */
        cnDetailDiv {String uniqueItemName = null ->
            if (uniqueItemName!=null){
                assert $('div.cn-detail-label',text:uniqueItemName)
                return $('div.cn-detail-label',text:uniqueItemName).closest('div.cn-detail')
            }
            else{

                return $('div.cn-detail')
            }

        }
    }

    /**
     *获取详情列表中所有的键值对
     * @param uniqueKeyName
     * @return
     */

    Map<String,String> getAllKeyValuePairs(String uniqueKeyName =null){
        log.info("获取${uniqueKeyName} 所在详情列表中的所有键值对")

        Map<String,String> resulyMap = [:]

        Navigator cDiv = cnDetailDiv(uniqueKeyName)
        assert cDiv,"页面上找不到包含${uniqueKeyName} 的详情页"
        cDiv.$('div.cn-detail-label').each {
            String key = it.text()
            String value = it.next('div.cn-detail-value').text()
            resulyMap[key] = value
        }
        return resulyMap
    }

    /**
     * 切换到指定名称的内部tab
     * @param tabName
     * @return
     */

    def switchToInnerTab(String tabName){
        log.info("切换到名称为${tabName}的内部标签页")
        $('div.next-tabs-tab-inner',text: tabName).click()
    }



}
