package uitestlib.uicommon.modules

import geb.navigator.Navigator

/**
 * 未命名文本区域输入
 * Created by QingHuan on 2019/11/10 16:14
 */
class UnNamedTextAreaInput extends StableEnhancedMoudle{
    static content ={
        unNamedTextAreaInput {String placeHolderText ,int index = 0 ->
            $('textarea',placeHolderText:contains(placeHolderText),index)
        }
    }

    def inputTextToUnNamedTextAreaInputField(String placeHolderText,String value,int index =0){
        Navigator input = unNamedTextAreaInput(placeHolderText,index)
        assert input,"页面上没有找到placeHolderText属性为 ${placeHolderText},index为:${index}的元素"
        input.firstElement().clear()
        input << value
    }

}
