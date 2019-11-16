package uitestlib.walle.modules

import geb.navigator.Navigator

/**
 * Created by QingHuan on 2019/11/17 0:44
 */
class ZTagGroup extends Field{

    def selectTagFieldByTagText(String fieldName,Integer fieldIndex =0 ,String...value){
        log.info("为给定字段选择标签，${fieldName}，${fieldIndex}，${value}")
        Navigator lbDiv = labelDiv(fieldName,fieldIndex)
        assert lbDiv,"页面上找不到名称为${fieldName}的字段"
        selectTagFieldByTagText(lbDiv,value)
    }

    def selectTagFieldByTagText(Navigator lbDiv,String...value){
        Navigator theDiv = controlDivByNavigator(lbDiv)
        selectTagNoFieldByTagText(theDiv,value)
    }

    def selectTagNoFieldByTagText(Navigator ctrlDiv,String...value){

        log.info("选择标签:${value}")

        Navigator theDiv = ctrlDiv
        value.eachWithIndex{ String entry, int i ->
            Navigator tag = theDiv.$('a.z-tag-item',text:entry)
            if (!tag.attr('class').contains('z-checked')){
                stableClick(tag)
            }
        }
    }



    
    List<String> getValuesForField(String fieldName,Integer fieldIndex = 0){
        Navigator lbDiv = labelDiv(fieldName,fieldIndex)
        Navigator ctrlDiv = controlDivByNavigator(lbDiv)

        List<String> resultList = []
        ctrlDiv.$('a.z-tag-item').each {
            if (it.attr('class').contains('z-checked')){
                resultList.add(it.text())
            }
        }
        resultList

    }

}
