#modules 页面基础能力建模
##CheckBoxSelector 页面复选框
    1：checkOnCheckBox 勾选
    2：checkOffCheckBox 取消勾选
## FileUpload   建模上传文件
    1：upLoadFile 上传文件
## Menu 菜单栏
    1：clickMenuItemsInOrder 按顺序点击菜单
## NameButton 按钮点击
    1：clickNameButton 按文本点击按钮
## NamedDiv div元素文本点击
    1：clickNamedDiv按文本点击Div元素
## NameLink 链接文本点击
    1：clickNamedLink 按文本点击链接
## RadioButton 单选按钮
    1：checkOffRadioButton 取消勾选单选框
    2：checkOnRadioButton    勾选单选框
## ScrollableDiv 页面滑动组件
    1:scrollFromTopToDown   从上到下滑动组件
    2:scrollFromLeftToRight  从左到右滑动组件
## StableEnhancedMoudle 基础能力 
    1：stableClick js点击
    2：forceClearAndSetValue 强制清除当前文本并设置新值
    3：getTextByJavaScript  通过js脚本获取常规方法无法获取到的文本
    4：creatNavigatorfromElement 将element对象转换成Navigator对象
    5：ignoreStaleElement  执行闭包中的代码 ，忽略 stale element reference：element is not attached to the page document异常
    6：getChildrenRecursively 递归获取 base元素下所有子元素
    7：triggerUntilOpened 点击下拉按钮直到展开弹出框 ，最多点4此
    8：findNewOpendOverlay 执行必包动作后，查看是否有新的弹出层，如果有，就返回
    9：triggerUntilClosed 点击下拉按钮，直到闭合，最多点击4次
    10：autowWithFrame 执行代码查找元素出错后，自动到iframe中查找，最多支持两层iframe
    11：findTextTypeInputElement 在base元素下查找<input type = text 的输入元素。为了做兼容，如果查不到 就查找input标签，但是无显示type属性的元素
    12：clickWhiteArea 点击元素附近的空白处
## UnNamedTextAreaInput 未命名文本区域输入
    1：inputTextToUnNamedTextAreaInputField 未命名输入区域输入文本
## UnNamedTextInput 未命名选择
    1：inputTextToUnNamedTextInputField 未命名输入文本框输入文本