import React, { Component } from 'react';
import NavBar from '../TemplatePage/NavBar';
import Header from '../TemplatePage/Header';
import { connect } from 'react-redux';
import Button from '../_components/Button';
import {employeeActions} from '../_actions/employee.actions';
import {projectActions} from '../_actions/project.actions';
import {mypageActions} from '../_actions/mypage.actions';
import TableComponent from '../_components/TableComponent/TableComponent';
import convertData from '../_convertData/convertData';
import Pagination from '../_components/PaginationComponentGoogle';
import styles from './css/pages.css';

class AddEmployee extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            pages:0,
            page: 1,
            sizePerPage:10,
            pageOfItems: [],
            isFirst: true,
            project_id:56 ,
            valueAddEmployee: []
        };
        this.handleAddEmployeed=this.handleAddEmployeed.bind(this);
    }
    componentDidMount() {
        this.fetchData();
        
            
    }
  
    componentWillMount(){
        console.log(this.props.match.params.project_id);
        let project_id = this.props.match.params.project_id;
        if(project_id){
        this.setState({
            project_id: project_id
        });
        }
    }
    fetchData(project_id,page,pageSize){
        if(project_id === undefined) project_id =this.state.project_id;
        if(page === undefined) page =this.state.page;
        if(pageSize === undefined) pageSize = this.state.sizePerPage;
       // this.props.dispatch(employeeActions.getListEmployeeInProject(project_id,page,pageSize));
         this.props.dispatch(employeeActions.getListEmployeeNotInProject(project_id,page,pageSize));
    }

    handleAddEmployeed(data){
        var employee=[];
        employee=data;
        employee.push(this.state.project_id);
       
        console.log(employee);
        //for(var i=0;i<data.length; i++){
            this.props.dispatch(projectActions.performAddEmployee(employee));
       // }
    }

    handleAddEmployee(data){
        const value = [];
        for(var i=0;i<data.length;i++){
            value.push(data[i]);
        }
        this.setState({
            valueAddEmployee: value
        });
    }
    onChangePage(page,sizePage) {
        // alert(page);
        if(page){
            this.fetchData(page,sizePage);
        }
           
    }
    render(){
        const {employeeList,employeeListNotIn,loadTwo ,pages,projectList }  = this.props;
        var name;
        if(projectList){
            for(var i=0;i<this.props.projectList.length;i++){
                if(this.state.project_id == this.props.projectList[i].project_id){
                    name=this.props.projectList[i].name;
                    break;
                }
            }
        }
        return(
            <div>
                <NavBar/>
                <Header/>
                <div className="right_col" role="main">
                <h2 className={styles.text_Header}>Project: "{name}"</h2>
                    <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12" >
                    <div className="col-xs-5 col-sm-5 col-md-5 col-lg-5" >
                        <div className="panel panel-primary">
                            <div className="panel-heading">
                                <h3 className="panel-title text-center" >
                                    List Employee : 
                                </h3>
                                </div>
                                    <div className="panel-body">
                                    {employeeListNotIn &&
                                    <TableComponent  data = {convertData(employeeListNotIn ,["Name Employee","Group","Type",],['fullname','group_id','type_name'],'employee_id',[{'sortedColumn':'fullname','direction':'desc'},{'sortedColumn':'type_name','direction':'desc'}])}
                                    isCheckbox={true} onApprove={ this.handleAddEmployee.bind(this) }
                                    />}
                                    <Pagination items={employeeListNotIn} pages={pages} onChangePage={this.onChangePage.bind(this)}/>
                                </div>
                                </div>
                        </div>
                        <div className="col-xs-1 col-sm-1 col-md-1 col-lg-1" >
                                    <Button onClick={() => this.handleAddEmployeed(this.state.valueAddEmployee)}> Add Employee</Button>
                        </div>
                    <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6" >
                        <div className="panel panel-primary">
                            <div className="panel-heading">
                                <h3 className="panel-title text-center" >
                                    List Employee In Project: 
                                </h3>
                            </div>
                            <div className="panel-body">
                                    {employeeList && 
                                    <TableComponent data = {convertData(employeeList,["Name Employee","Group","Type",],['fullname','group_id','type_name'],'project_id',[{'sortedColumn':'fullname','direction':'desc'},{'sortedColumn':'type_name','direction':'desc'}])}/>}
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
        )
    }
}
function mapStateToProps(state) {
    const { isOpen } = state.popup;
    const { employeeList , pages, loading, employeeListNotIn,loadTwo  } = state.employees;
    const {projectList}=state.project;
    return {
        isOpen,
        employeeList,
        loading,
        pages,
        employeeListNotIn,
        loadTwo,
        projectList
    

    };
}
const connectedAddEmployee = connect(mapStateToProps)(AddEmployee);
export  { connectedAddEmployee as AddEmployee};
