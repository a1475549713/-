package uitestlib.walle.modules

import geb.navigator.Navigator

import javax.print.attribute.standard.MediaSize.NA

/**
 * Created by QingHuan on 2019/11/17 0:34
 */
class ZAddressGroup extends Field{

    /**
     * 输入地址，按实际情况填写 省 市 区
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


    def inputAddressForField(Navigator fieldName,String...addresses){
        log.info("设置地址：${fieldName},${addresses}")
        inputAddressForNoField(controlDivByNavigator(fieldName),addresses)
    }


    def inputAddressForNoField(Navigator ctrlDiv,String...address){
        log.info("设置地址：${address}")
        address.eachWithIndex{ String entry, int i ->
            log.info("输入地址：${entry}")
            waitFor (20){
                Navigator input = ctrlDiv.$('input',type:'text',i)
                input?.parent('span')?.click()
                sleep(500)
                Navigator a = $('li',text:addr)
                if (a){
                    a.click()
                    return true
                }
                else {
                    return false
                }
            }
        }
    }

    List<String> getAddressForField(String fieldName,Integer fieldIndex=0){
        log.info("获取给定页面字段值${fieldName}，${fieldIndex}")
        getAddressForField(controlDiv(fieldName,fieldIndex))
    }

    static List<String> getAddressForField(Navigator ctrlDiv){
        List<String> resultList = []
       Navigator addressElements = ctrlDiv.$('em')
        addressElements.each {
            resultList.add(it.text())
        }
        resultList
    }


}
