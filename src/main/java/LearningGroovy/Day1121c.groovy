package LearningGroovy

/**
 * Created by QingHuan on 2019/11/21 22:44
 */

class Day112c {
    //搬砖的
    def work(){println("i am doing")}
    def analyze(){println("analyze")}
    def writeReport(){println("get report written")}
}

class Day112c1{
    //也是搬砖的
    def analyze(){println("Day112c1analyze")}
}


class Manger{
    //包工头
    @Delegate Day112c1 day112c1 = new Day112c1()
    @Delegate Day112c day112c = new Day112c()

}

//老板来找包工头干活

def boos = new Manger()
//包工头找搬砖的干活
//如果有同名方法，按委托顺序来，
boos.work()
boos.analyze()
boos.writeReport()


