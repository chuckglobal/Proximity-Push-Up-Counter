package com.awesome.scottquach.proximitypush_upcounter.features.Statistics;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Scott Quach on 11/24/2017.
 */

public class StatisticsPresenter implements StatisticsDatabase.StatisticsDatabaseCallback{

    private StatisticsFragment view;
    private StatisticsDatabase database;


    public StatisticsPresenter(StatisticsFragment view) {
        this.view = view;
        this.database = new StatisticsDatabase(view.getContext(), this);
    }

    public void loadData() {
        database.requestTotalPushups();
        database.requestHighScore();
        database.requestDayHighScore();
        database.requestTimesGoalFailed();
        database.requestTimesGoalReached();
        database.requestTotalDayPushups();
        database.requestGraphData();
    }

    public void onViewDestroyed() {
        database.disposeObservables();
    }


    @Override
    public void totalPushupsLoaded(int total) {
        view.setTotalPushups(total);
    }

    @Override
    public void highScoreLoaded(int highscore) {
        view.setSessionHighScore(highscore);
    }

    @Override
    public void dayHighScoreLoaded(int highscore) {
        view.setDayHighScore(highscore);
    }

    @Override
    public void timesGoalReached(int num) {
        view.setTimesGoalReached(num);
    }

    @Override
    public void timesGoalFailed(int num) {
        view.setTimesGoalFailed(num);
    }

    @Override
    public void totalDayPushupsLoaded(int total) {
        view.setTodayTotalPushups(total);
    }

    @Override
    public void graphDataLoaded(LineGraphSeries<DataPoint> series) {
        view.setGraph(series);
    }
}
