package uitestlib.walle.modules


import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import uitestlib.uicommon.modules.StableEnhancedMoudle
/**
 * Created by QingHuan on 2019/11/14 23:21
 */


@Slf4j
class Menu  extends StableEnhancedMoudle{
    static content = {
        menuItem { String menuText -> $('div.cn-navfold-title',text:menuText)
        }
    }

    /**
     * 按顺序逐级点击菜单 ：
     * @param menuTextString
     * @return
     */
    def clickMenuItemsInOrder(String...menuTextString){
        log.info("按顺序逐级点击页面左侧菜单：${menuTextString}")
        if (menuTextString){
            menuTextString.each {
                identifyMenuTypeAndClick(it)
            }
        }
    }

    /**
     * 识别菜单类型，点击菜单
     * @param menuText
     * @return
     */

    private def identifyMenuTypeAndClick(String menuText){
        for (int i in 0..10){
            try{
                Navigator divs = $('div.cn-navfold-title')+$('div.cn-topnav-bar-btn')
                for (Navigator div in divs){
                    if (div.text() == menuText){
                        stableClick(div)
                        return
                    }
                }
                Navigator atags = $('div.cn-topnav-flow-submenu-menu-title a')
                for (Navigator a in atags){
                    if (a.text()==menuText){
                        stableClick(a)
                        return
                    }
                }
            }
            catch (Throwable ignored){
                log.info("查找菜单发生异常${ignored.message}")
            }
            sleep(600)
        }
        throw new Exception("无法找到给定的菜单${menuText}")
    }

}
