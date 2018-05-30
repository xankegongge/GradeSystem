package com.example.data.myclass;

/**
 * Created by xanke on 2017-11-19.
 */

public class Student {
    private  int m_ExaminationNum;//考试号码
    private  int m_GroupNum;//组号
    private String m_AdmissionTicket;//准考证号
    private InterviewState m_InterviewState=InterviewState.未面试;//面试状态
    private int m_PerfessionalCategory;//专业
    private String m_StudentNum;//学号

    public int get_ExaminationNum() {
        return m_ExaminationNum;
    }

    public void set_ExaminationNum(int m_ExaminationNum) {
        this.m_ExaminationNum = m_ExaminationNum;
    }

    public int get_GroupNum() {
        return m_GroupNum;
    }

    public void set_GroupNum(int m_GroupNum) {
        this.m_GroupNum = m_GroupNum;
    }

    public String get_AdmissionTicket() {
        return m_AdmissionTicket;
    }

    public void set_AdmissionTicket(String m_AdmissionTicket) {
        this.m_AdmissionTicket = m_AdmissionTicket;
    }

    public InterviewState get_InterviewState() {
        return m_InterviewState;
    }

    public void set_InterviewState(InterviewState m_InterviewState) {
        this.m_InterviewState = m_InterviewState;
    }

    public int get_PerfessionalCategory() {
        return m_PerfessionalCategory;
    }

    public void set_PerfessionalCategory(int m_PerfessionalCategory) {
        this.m_PerfessionalCategory = m_PerfessionalCategory;
    }

    public String get_StudentNum() {
        return m_StudentNum;
    }

    public void set_StudentNum(String m_StudentNum) {
        this.m_StudentNum = m_StudentNum;
    }

    public boolean is_IsPayed() {
        return m_IsPayed;
    }

    public void set_IsPayed(boolean m_IsPayed) {
        this.m_IsPayed = m_IsPayed;
    }

    private boolean m_IsPayed;

}
