/*
package uitestlib.walle.modules

import geb.navigator.Navigator
import uitestlib.uicommon.modules.StableEnhancedMoudle

*/
/**
 * Created by QingHuan on 2019/11/17 12:22
 *//*

class ListPageTable extends StableEnhancedMoudle {

    static content = {
        overlay(wait:2){$('div.next-overlay-wrapper.opened',-1)}
    }
    ListPageTable(){
        */
/**
         * 表格行，表格体，行列的css选择器
         *//*

        HEAD_ROW_SELECTOR = 'div-next-row.list-header'
        BODY_ROW_SELECTOR = 'div-next-row.list-row'
        HEAD_COL_SELECTOR = 'div-next-col'
        BODY_COL_SELECTOR = 'div-next-col'

        */
/**
         * 同事包含tableheader和tablebode的最近一层div。该div用来定位和表格相关联的翻页组件
         *//*

        TABLE_DIV_SELECTOR = 'div.list-page'

        //翻页
        CLASS_OF_PAGING_DIV_SELECTOR = 'next-pagination'
        //向前 向后，到最后一页，等翻页按钮
        PREVIOUS_PAGE_BUTTON_SEKECTOR = 'button.next-pagination-item.prev'
        NEXT_PAGE_BUTTON_SELECTOR = 'button.next-pagination-item.next'
    }

    def setPageSizeForTable (Object uniqColNameOrTableIndex ,Object size){
        Navigator header = getTableHeaderNavigator(uniqColNameOrTableIndex)


    }



    Navigator getTableHeaderNavigator(Object uniqColNameOrTableIndex){
        long startTime = System.currentTimeMillis()
        log.info("查找表格头-----------------开始：${startTime}")
        Navigator header
        String cacheKey = getTestCaseName() + uniqColNameOrTableIndex + this.class.simpleName
        if (uniqColNameOrTableIndex instanceof  Integer){
            log.debug("用户直接使用表格索引定位表格，忽略cache")
            header = tableHeader
        }



    }




}
*/
