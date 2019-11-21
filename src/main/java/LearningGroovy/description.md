#这个包作为学习groovy的纪录随笔
##11/20 
1:操作符重载  day1120   没啥卵用
   如果想在一个类外面运行，注意类名与文件名不能一样。因为Groovyc编译时会默认查找文件与类
      如果名字一样则会找到两个类，不知道引用哪个
   坑 https://www.cnblogs.com/softidea/p/5122188.html 
##day11/21
1:enum day1121
2:常用注解
    @Canonical  day1121b ???
    @Delegate   day1121c 委托方法，有点东西！
    @Immutable  不可变对象 day1121d
    @Lazy   把耗时对象推迟到真正需要时才构建，比如ui自动化中浏览器初始化 day1121e
    @Newift 创建DSL 有点爽,可以穿件类似Python构造器不需要显示new xx   Day1121f