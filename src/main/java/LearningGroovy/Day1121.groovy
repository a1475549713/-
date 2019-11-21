package LearningGroovy

/**
 * Created by QingHuan on 2019/11/21 22:16
 */
enum Day112a {
    Evo(5),
    XP(21),
    Scrum(30) ;

    int Day
    def Day112a (day){
        Day = day
    }

    def dayIterable(){
        println("${this} recommends ${Day} days for iteration")
    }
}


for(d in Day112a.values()){

    d.dayIterable()
}
