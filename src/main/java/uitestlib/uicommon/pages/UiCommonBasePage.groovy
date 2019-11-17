package uitestlib.uicommon.pages

import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.internal.Coordinates
import uitestlib.uicommon.modules.CheckBoxSelector
import uitestlib.uicommon.modules.FileUpload
import uitestlib.uicommon.modules.NameButton
import uitestlib.uicommon.modules.NameLink
import uitestlib.uicommon.modules.NamedDiv
import uitestlib.uicommon.modules.RadioButton
import uitestlib.uicommon.modules.ScrollableDiv
import uitestlib.uicommon.modules.StableEnhancedMoudle
import uitestlib.uicommon.modules.UnNamedTextAreaInput
import uitestlib.uicommon.modules.UnNamedTextInput
import org.openqa.selenium.interactions.internal.Locatable

/**
 * 页面基本事件抽象
 * Created by QingHuan on 2019/11/10 16:09
 */
@SuppressWarnings('unused')
@Slf4j
class UiCommonBasePage extends ElementWaitingPage {

    /**
     * 在用户脚本中使用的导航器，用户查找元素后，找到的元素默认存在这里，后续中文方法不需要显示的提及要操作的元素
     */
    Navigator navigatorInScript = null

    /**
     * 执行切换窗口前，浏览器所在的窗口
     */
    String originalWindow = null

    static at = {true}

    static content = {
        //可滑动的div组件
        scrollableDiv {module( ScrollableDiv)}

        //文件上传
        fileUpload {module FileUpload}

        //稳定性真强组件
        stableEnhanceMoudle {module(StableEnhancedMoudle)}
        //不具名的数据元素
        unNamedInput {module(UnNamedTextInput)}
        //不具名的文本区域输入元素
        unNamedTextAreaInput {module(UnNamedTextAreaInput)}

        //命名的链接元素
        namedLink {module(NameLink)}

        //命名的div元素
        namedDiv {module(NamedDiv)}
        //命名的按钮元素
        nameButton {module(NameButton)}
        //复选框
        checkBoxSelector{module(CheckBoxSelector)}
        //单元按钮
        radioButton {module(RadioButton)}
    }


    /**
     * 无视遮挡点击元素
     * @param navigator
     * @return
     */
    def jsClick(Navigator navigator){
        js.exec(navigator.firstElement(),'arguments[0].click();')
    }

    /**
     * 无视遮挡点击元素
     * @param navigator
     * @return
     */
    def stableClick(Navigator navigator){
        stableEnhanceMoudle.stableClick(navigator)
    }


    def stableClick(WebElement element){
        stableEnhanceMoudle.stableClick(element)
    }

    /**
     * 强制清除当前文本值，并设置成新值
     * @param niv
     * @param value
     * @return
     */
    def forceClearAndSetValue(Navigator niv,String value){
        stableEnhanceMoudle.forceClearAndSetValue(niv,value)
    }


    def forceClearAndSetValue(WebElement element,String value){
        stableEnhanceMoudle.forceClearAndSetValue(element,value)
    }

    String getTextByJavaScript(Navigator niv){
        stableEnhanceMoudle.getTextByJavaScript(niv.firstElement())

    }

    String getTextByJavaScript(WebElement element){
        stableEnhanceMoudle.getTextByJavaScript(element)
    }

    Navigator creatNavigatorfromElement(WebElement element){
        stableEnhanceMoudle.creatNavigatorfromElement(element)
    }


    /**
     * 点击事件
     * @param buttonName
     * @param index
     * @param strict
     * @return
     */
    def clickNamedButton(String buttonName,Integer index = 0,boolean strict = false){
        log.info("点击按钮：buttonName：$buttonName,index:$index,strict:$strict")
        if (strict){
            nameButton.clickNameButton(buttonName,index)
        }
        else {
            log.info("使用非严格测试：<button <a ,<input type:submit 等类型都会当做按钮执行检查")
            waitFor (message:"页面上找不到名称为${buttonName},$index 的按钮元素，请确认是否写作"){
                Navigator theElement = $('button',text:contains(buttonName),index)
                if (theElement){
                    log.info("找到了匹配的<button元素")
                    nameButton.clickNameButton(buttonName,index)
                    return true
                }
                //查找 <input type:submit
                Navigator theElements = $('input[type="submit"]',value:contains(buttonName))
                if (theElements){
                    Navigator sorted = stableEnhanceMoudle.chooseWhenMoreThanOneMatch(theElements,buttonName)
                    if (sorted.size()>index){
                        log.info("找到了匹配的input[type=\"submit\"]")
                        stableClick(sorted[index])
                        return true
                    }
                    else{
                        log.info("找到了匹配的input[type=\"submit\"],但是索引不满足要求 ，预期索引$index,页面上只有${sorted.size()}个元素")
                        return false
                    }
                }
                Navigator theElement2 = $('a',text: contains(buttonName),index)
                if (theElement2){
                    log.info("找到了匹配的<a元素")
                    namedLink.clickNamedLink(buttonName,index)
                    return true
                }
                return false
            }
        }
    }



    def clickNamedLink(String linkTest,Integer index = 0){
        namedLink.clickNamedLink(linkTest,index)
    }

    def clcikNamedDiv(String divText){
        namedDiv.clickNamedDiv(divText)
    }

    /**
     * 刷新页面
     */
    def refreshPage(){
        browser.driver.navigate().refresh()
    }

    /**
     * 页面前进
     * @return
     */
    def forward(){
        browser.driver.navigate().forward()
    }

    /**
     * 后退
     * @return
     */
    def back(){
        browser.driver.navigate().back()
    }


    /**
     * 上传文件
     * @param uploadButtonName
     * @param filePath
     * @param buttonIndex
     * @return
     */
    def uploadFile(String uploadButtonName,String filePath,Integer buttonIndex=0){
        fileUpload.upLoadFile(uploadButtonName,filePath,buttonIndex)
    }

    /**
     * 滚动条操作
     * @param element
     * @param percent
     * @return
     */
    def scrollFromLeftToRightByPercent(WebElement element,Integer percent){
        scrollableDiv.scrollFromLeftToRightByPercent(element,percent)
    }

    def scrollFromLeftToRightByPercent(Navigator navigator,Integer percent){
        scrollableDiv.scrollFromLeftToRightByPercent(navigator,percent)
    }

    def scrollFromLeftToRightByPercent(String navigator,Object textOrIndex = null,Integer percent){
        scrollableDiv.scrollFromLeftToRightByPercent(navigator,textOrIndex,percent)
    }

    def scrollFromTopToDownByPercent(WebElement navigator,Integer percent){
        scrollableDiv.scrollFromTopToDownByPercent(navigator,percent)
    }

    def scrollFromTopToDownByPercent(Navigator navigator,Integer percent){
        scrollableDiv.scrollFromTopToDownByPercent(navigator,percent)
    }

    def scrollFromTopToDownByPercent(String navigator,Object textOrIndex = null,Integer percent){
        scrollableDiv.scrollFromTopToDownByPercent(navigator,textOrIndex,percent)
    }

    /**
     * 文本输入事件
     * @param placeHolderText
     * @param value
     * @param index
     * @return
     */
    def inputTextToUnNamedTextInputField(String placeHolderText,String value,int index =0){
        unNamedInput.inputTextToUnNamedTextInputField(placeHolderText,value,index)
    }

    def inputTextToUnNamedTextAreaInputField(String placeHolderText,String value,int index =0){
        unNamedTextAreaInput.inputTextToUnNamedTextAreaInputField(placeHolderText,value,index)
    }


    /**
     * ifram处理
     */
    def switchToFrameByIndex(Integer index = 0){
        browser.driver.switchTo().frame(index)
    }

    def switchToFrameByIndex(Navigator frame){
        browser.driver.switchTo().frame(frame.firstElement())
    }

    def switchToFrameByIndex(WebElement frame){
        browser.driver.switchTo().frame(frame)
    }

    /**
     * 浏览器从iframe切回主界面
     */

    def switchToDefaultContent(){
        browser.driver.switchTo().defaultContent()
    }

    /**
     * 勾选 复选框
     * @param index
     * @return
     */
    def checkOnCheckBox(Integer index =0){
        checkBoxSelector.checkOnCheckBox(null,index)
    }

    def checkOnCheckBox(Navigator base,Integer index =0){
        checkBoxSelector.checkOnCheckBox(base,index)
    }

    def checkOnCheckBox(String name,Integer index =0){
        checkBoxSelector.checkOnCheckBox(null,name,index)
    }

    def checkOnCheckBox(Navigator base,String name,Integer index =0){
        checkBoxSelector.checkOnCheckBox(base,name,index)
    }

    /**
     * 取消勾选 复选框
     * @param index
     * @return
     */
    def checkOffCheckBox(Integer index=0){
        checkBoxSelector.checkOffCheckBox(index)
    }

    def checkOffCheckBox(Navigator base,Integer index=0){
        checkBoxSelector.checkOffCheckBox(base,index)
    }
    def checkOffCheckBox(String name,Integer index=0){
        checkBoxSelector.checkOffCheckBox(null,name,index)
    }
    def checkOffCheckBox(Navigator base,String name,Integer index=0){
        checkBoxSelector.checkOffCheckBox(base,name,index)
    }
    /**
     * 勾选，单选框
     * @param index
     * @return
     */
    def checkOnRadioButton(Integer index = 0){
        radioButton.checkOnRadioButton(null,index)
    }

    def checkOnRadioButton(Navigator base,Integer index=0){
        radioButton.checkOnRadioButton(base,index)
    }

    def checkOnRadioButton(String name,Integer index=0){
        radioButton.checkOnRadioButton(null,name,index)
    }

    def checkOnRadioButton(Navigator base,String name,Integer index=0){
        radioButton.checkOnRadioButton(base,name,index)
    }
    /**
     * 取消勾选，单选框
     * @param index
     * @return
     */

    def checkOffRadioButton(Integer index = 0){
        radioButton.checkOffRadioButton(null,index)
    }

    def checkOffRadioButton(Navigator base,Integer index=0){
        radioButton.checkOffRadioButton(base,index)
    }

    def checkOffRadioButton(String name,Integer index=0){
        radioButton.checkOffRadioButton(null,name,index)
    }

    def checkOffRadioButton(Navigator base,String name,Integer index=0){
        radioButton.checkOffRadioButton(base,name,index)
    }

    /**
     * 下面开始封装中文方法
     */

    def 刷新页面(){
        refreshPage()
    }
    def 前进(){
        forward()
    }
    def 后退(){
        back()
    }
    def 等待(Long time){
        sleep(time)
    }

    Navigator 查找元素(Map<String,Object> attributes,String selector,int index){
        log.info("查找元素attributes：$attributes,selector:$selector,index:$index")
        navigatorInScript = waitForElement(attributes,selector,index)
        return navigatorInScript
    }

    Navigator 查找元素(Map<String,Object> attributes,String selector){
        log.info("查找元素attributes：$attributes,selector:$selector")
        navigatorInScript = waitForElement(attributes,selector)
        return navigatorInScript
    }

    Navigator 查找元素(String selector,int index){
        log.info("查找元素:selector:$selector,index:$index")
        navigatorInScript = waitForElement(selector,10,index)
        return navigatorInScript
    }

    Navigator 查找元素(String selector){
        log.info("查找元素:selector:$selector")
        navigatorInScript = waitForElement(selector,10)
        return navigatorInScript
    }

    def 点击按钮(String buttonName,Integer index = 0){
        clickNamedButton(buttonName,index,false)
    }
    def 点击链接(String buttonName,Integer index = 0){
        clickNamedLink(buttonName,index)
    }
    /**
     * 点击级联按钮
     * @param buttonName
     * @param liText
     * @return
     */
    def 点击级联按钮(String buttonName,String...liText){
        log.info("点击级联按钮：buttonName：${buttonName},liText:$liText")
        点击按钮(buttonName)
        liText.each {String text ->
            def niv = element('li',text:text)
            stableClick(niv)
        }
    }

    def 点击元素(Map<String,Object> attributes,String selector,int index){
        log.info("点击元素attributes：$attributes,selector:$selector,index:$index")
        def niv = waitForElement(attributes,selector,index)
        assert niv,"没有找到指定元素attributes：$attributes,selector:$selector,index:$index"
        stableClick(niv)
    }

    def 点击元素(Map<String,Object> attributes,String selector){
        log.info("点击元素attributes：$attributes,selector:$selector,")
        def niv = waitForElement(attributes,selector)
        assert niv,"没有找到指定元素attributes：$attributes,selector:$selector,"
        stableClick(niv)
    }

    def 点击元素(String selector,int index){
        log.info("点击元素s,selector:$selector,index:$index")
        def niv = waitForElement(selector,10,index)
        assert niv,"没有找到指定元素selector:$selector,index:$index"
        stableClick(niv)
    }

    def 点击元素(String selector){
        log.info("点击元素s,selector:$selector,")
        def niv = waitForElement(selector,10)
        assert niv,"没有找到指定元素selector:$selector"
        stableClick(niv)
    }

    def 点击元素(){
        assert navigatorInScript,"请先查找元素，再点击"
        stableClick(navigatorInScript)
    }

    def 输入文本(String text){
        assert navigatorInScript,"请先查找元素，再点击"
        forceClearAndSetValue(navigatorInScript,text)
    }
    String 获取元素文本(String tag,String textOrPlaceholder,Integer index = 0){
        Navigator elem = $(tag,text:contains(textOrPlaceholder),index)
        Navigator elem2 = $(tag,placeHolder:textOrPlaceholder(textOrPlaceholder),index)
        Navigator theElem =elem+elem2
        assert elem,"不是一个页面有效元素，无法获取文本"
        getTextByJavaScript(theElem)
    }
    String 获取元素文本(String tag,Integer index = 0){
        Navigator elem = $(tag,index)
        assert elem,"不是一个页面有效元素，无法获取文本"
        getTextByJavaScript(elem)
    }
    String 获取元素文本(Navigator tag){
        assert tag,"不是一个页面有效元素，无法获取文本"
        getTextByJavaScript(tag)
    }

    String 获取元素文本(){
        assert navigatorInScript,"请先通过元素查找后，再获取文本"
        getTextByJavaScript(navigatorInScript)
    }

    String 获取元素属性(String attrName){
        assert navigatorInScript,"请先通过元素查找后，再获取属性"
        return navigatorInScript.attr(attrName)
    }

    String 获取元素属性(Navigator niv,String attrName){
        assert niv,"请先通过元素查找后，再获取属性"
        return niv.attr(attrName)
    }

    String 获取页面文本(){
        browser.driver.getPageSource()
    }

    def 等待页面包含文本(String text,Integer timeout = 30){
        waitFor(message: "等待超时，${timeout}秒内页面上并未出现文本${text}",timeout,2){
            获取页面文本().contains(text)
        }
    }

    def 刷新并等待页面包含文本(String text,Integer timeout = 30){
        refreshWaitFor (message: "等待超时，${timeout}秒内页面上并未出现文本${text}",timeout,2){
            获取页面文本().contains(text)
        }
    }

    def 校验页面包含文本(String text,Integer timeout){
        waitFor(message: "等待超时，${timeout}秒内页面上并未出现文本${text}",timeout){
            获取页面文本().contains(text)
        }
    }
    def 校验页面包含文本(String text){
        waitFor(message: "等待超时，页面上并未出现文本${text}"){
            获取页面文本().contains(text)
        }
    }
    def 刷新并校验页面包含文本(String text,Integer timeout ){
        refreshWaitFor (message: "等待超时，${timeout}秒内页面上并未出现文本${text}",timeout,){
            获取页面文本().contains(text)
        }
    }
    def 刷新并校验页面包含文本(String text ){
        refreshWaitFor (message: "等待超时，页面上并未出现文本${text}",){
            获取页面文本().contains(text)
        }
    }

    def 获取页面标题(){
        browser.title
    }
    def 校验页面标题包含文本(String text){
        waitFor(message: "等待超时，页面上并未出现文本${text}"){
            获取页面标题().contains(text)
        }
    }

    String 获取页面URL(){
        browser.currentUrl
    }

    def 校验页面URL包含文本(String text){
        waitFor(message: "等待超时，页面上并未出现文本${text}"){
            获取页面URL().contains(text)
        }
    }

    def 切换到新打开的窗口(){
        originalWindow = browser.currentWindow
        def a = browser.availableWindows
        assert a.size()>1,"当前仅有一个窗口，没有新打开的窗口"
        browser.driver.switchTo().window(a[-1])
    }

    def 切换到原窗口(){
        browser.driver.switchTo().window(originalWindow)
    }

    def 在新窗口的上下文中执行操作(Closure closure){
        originalWindow = browser.currentWindow
        def a = browser.availableWindows
        assert a.size()>1,"当前仅有一个窗口，没有新打开的窗口"
        def newWindowHandler = a[-1]
        Closure wrapper = addSleepToEndOfClosure(closure)
        browser.withWindow(wait:true,closure:true,newWindowHandler,wrapper)
    }

    def 在指定窗口的上下文中执行操作(Closure window,Closure closure){
        Closure wrapper = addSleepToEndOfClosure (closure)
        browser.withWindow(wait:true,close:true,window,wrapper)
    }

    static Closure addSleepToEndOfClosure(Closure c){
        Closure wrapper={
            c.call()
            sleep(5000)
        }
        return wrapper
    }

    def 选中复选框(Integer index = 0){
        checkOnCheckBox(index)
    }

    def 选中复选框(Integer...indexs){
        indexs.each {Integer index ->
            checkOnCheckBox(index)
        }
    }

    def 选中复选框(String name,Integer index = 0){
        checkOnCheckBox(name,index)
    }

    def 选中复选框(List<String> names,Integer index = 0){
        names.each {String name ->
            checkOnCheckBox(name,index)
        }
    }

    def 取消选中复选框(Integer index = 0){
        checkOffCheckBox(index)
    }
    def 取消选中复选框(Integer...indexs){
        indexs.each {Integer index ->
            checkOffCheckBox(index)
        }
    }

    def 取消选中复选框(String name,Integer index = 0){
        checkOffCheckBox(name,index)
    }

    def 取消选中复选框(List<String> names,Integer index = 0){
        names.each {String name ->
            checkOffCheckBox(name,index)
        }
    }

    def 选中单选按钮(Integer index = 0){
        checkOnRadioButton(index)
    }

    def 选中单选按钮(String name ,Integer index = 0){
        checkOnRadioButton(name,index)
    }

    def 取消选中单选按钮(Integer index = 0){
        checkOffRadioButton(index)
    }

    def 取消选中单选按钮(String name,Integer index = 0){
        checkOffRadioButton(index)
    }

    def 从上到下滑动滚动条(String cssSelector,Object textOrIndex = null,Integer percent = 100){
        scrollFromTopToDownByPercent(cssSelector,textOrIndex,percent)
    }
    def 从左到右滑动滚动条(String cssSelector,Object textOrIndex = null,Integer percent = 100){
        scrollFromLeftToRightByPercent(cssSelector,textOrIndex,percent)
    }

    def 将元素所在区域的滚动条拖到底部(){
        assert navigatorInScript,"请先通过“查找元素”方法查找到要操作的元素后，才能操作其所在区域的滚动条"
        def e = navigatorInScript
        操作元素所在区域的滚动条(e, Keys.PAGE_DOWN)
    }

    def 将元素所在区域的滚动条拖到右侧(){
        assert navigatorInScript,"请先通过“查找元素”方法查找到要操作的元素后，才能操作其所在区域的滚动条"
        def e = navigatorInScript
        操作元素所在区域的滚动条(e, Keys.ARROW_RIGHT)
    }
    def 将元素所在区域的滚动条拖到顶部(){
        assert navigatorInScript,"请先通过“查找元素”方法查找到要操作的元素后，才能操作其所在区域的滚动条"
        def e = navigatorInScript
        操作元素所在区域的滚动条(e, Keys.PAGE_UP)
    }
    def 将元素所在区域的滚动条拖到左侧(){
        assert navigatorInScript,"请先通过“查找元素”方法查找到要操作的元素后，才能操作其所在区域的滚动条"
        def e = navigatorInScript
        操作元素所在区域的滚动条(e, Keys.ARROW_LEFT)
    }

    def 将元素所在区域的滚动条拖到底部(Navigator e){
        操作元素所在区域的滚动条(e,Keys.PAGE_DOWN)
    }

    def 将元素所在区域的滚动条拖到顶部(Navigator e){
        操作元素所在区域的滚动条(e,Keys.PAGE_UP)
    }
    def 将元素所在区域的滚动条拖到左侧(Navigator e){
        操作元素所在区域的滚动条(e,Keys.ARROW_LEFT)
    }

    def 将元素所在区域的滚动条拖到右侧(Navigator e){
        操作元素所在区域的滚动条(e,Keys.ARROW_RIGHT)
    }

    def 操作元素所在区域的滚动条(Navigator e,Keys keys){
        assert  e,"页面上找不到该元素，无法执行滚动操作"
        Navigator target
        if (e.tag()!='div'){
            //如果不是div元素就找到最近的div元素
            def d = e.closest('div')
            if (!d){
                //如果没有找到div，就找body元素
                d = e.closest('body')
            }
            //聚焦
            d.click()
            target = d
        }
        else {
            e.click()
            target =e
        }
        int x = target.x
        int y = target.y
        Integer count = 10
        if (keys.name() ==Keys.ARROW_LEFT.name()||keys.name() ==Keys.ARROW_RIGHT.name()){
            count == 100
        }
        for (int i in 0..count){
            interact {
                sendKeys(keys)
            }
            sleep(100)
            if (x!=target.x||y!=target.y){
                log.info("执行${i}次滚动后，元素已滚到尽头")
                break
            }
        }
    }


    def 将元素移入视野(Navigator navigator){
        assert navigator,"页面上找不到该元素，无法执行滚动操作"
        def e= navigator
        def ec =((Locatable) (e.firstElement())).getCoordinates()
        ec.inViewPort()
    }

    def 将元素移入视野(){
        assert navigatorInScript,"请先通过“查找元素”方法查找到要操作的元素后，才能将元素移入视野"
        Coordinates ec =((Locatable) (navigatorInScript.firstElement())).getCoordinates()
        ec.inViewPort()
    }

    def 执行JS脚本(Object...args){
        js.exec(args)
    }

    def 将鼠标移动到元素上(int xOffser = 0,int yOffset=0){
        assert navigatorInScript,"请先通过“查找元素”方法查找到要操作的元素后，才能将元素移入视野"
        interact {
            moveToElement(navigatorInScript,xOffser,yOffset)
        }
    }
    def 将鼠标移动到元素上(Navigator navigator,int xOffser = 0,int yOffset=0){
        assert navigator,"navigator不是一个有效元素"
        interact {
            moveToElement(navigator,xOffser,yOffset)
        }
    }

    def 使用鼠标点击元素(){
        assert navigatorInScript,"请先通过“查找元素”方法查找到要操作的元素后，才能使用鼠标点击元素"
        interact {
            click(navigatorInScript)
        }
    }
    def 使用鼠标点击元素(Navigator navigator){
        assert navigator,"navigator不是一个有效元素"
        interact {
            click(navigator)
        }
    }

    def 使用鼠标在当前位置点击(){
        interact {
            click()
        }
    }

    def 鼠标右击(){
        assert navigatorInScript,"请先通过“查找元素”方法查找到要操作的元素后，才能使用鼠标点击元素"
        interact {
            contextClick(navigatorInScript)
        }
    }

    def 鼠标右击(Navigator navigator){
        assert navigator,"navigator不是一个有效元素"
        interact {
            contextClick(navigator)
        }
    }

    def 使用鼠标在当前位置右击(){
        interact {
            contextClick()
        }
    }
    def 鼠标双击(){
        assert navigatorInScript,"请先通过“查找元素”方法查找到要操作的元素后，才能使用鼠标点击元素"
        interact {
            doubleClick(navigatorInScript)
        }
    }
    def 鼠标双击(Navigator navigator){
        assert navigator,"navigator不是一个有效元素"
        interact {
            doubleClick(navigator)
        }
    }

    def 使用鼠标在当前位置双击(){
        interact {
            doubleClick()
        }
    }

    def 按住鼠标左键不放(){
        assert navigatorInScript,"请先通过“查找元素”方法查找到要操作的元素后，才能使用鼠标点击元素"
        interact {
            clickAndHold(navigatorInScript)
        }
    }
    def 按住鼠标左键不放(Navigator navigator){
        assert navigator,"navigator不是一个有效元素"
        interact {
            clickAndHold(navigator)
        }
    }

    def 当前位置按住鼠标左键不放(){
        interact {
            clickAndHold()
        }
    }

    def 在当前元素上松开鼠标(){
        assert navigatorInScript,"请先通过“查找元素”方法查找到要操作的元素后，才能使用鼠标点击元素"
        interact {
            clickAndHold(navigatorInScript)
        }
    }

    def 在当前元素上松开鼠标(Navigator navigator){
        assert navigator,"navigator不是一个有效元素"
        interact {
            clickAndHold(navigator)
        }
    }

    def 在当前位置松开鼠标(){
        interact {
            clickAndHold()
        }
    }
    def 使用鼠标从一个元素拖拽到另一个元素(Navigator source,Navigator target){
        assert source,"${source}不是一个有效元素"
        assert target,"${target}不是一个有效元素"
        interact {
            dragAndDrop(source,target)
        }
    }
    def 使用鼠标拖拽到指定偏移处释放(Navigator source,int xOffset,int yOffset){
        assert source,"${source}不是一个有效元素"
        interact {
            dragAndDropBy(source,xOffset,yOffset)
        }
    }

}

