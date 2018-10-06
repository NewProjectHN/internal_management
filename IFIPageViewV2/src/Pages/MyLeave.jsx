import React, { Component } from 'react';
import NavBar from '../TemplatePage/NavBar';
import Header from '../TemplatePage/Header';
import { Router, Route } from 'react-router';
import { connect } from 'react-redux';
import { leaveActions } from '../_actions/leave.actions';
import Pagination from '../_components/PaginationComponentGoogle';
import convertData from '../_convertData/convertData.js';
import TableComponent from '../_components/TableComponent/TableComponent';
import Button from '../_components/Button';
import {AddComponent} from '../_components/AddComponent/AddComponent';
import { confirmAlert } from 'react-confirm-alert'; // Import
import 'react-confirm-alert/src/react-confirm-alert.css' // Import css
import { mypageActions } from '../_actions/mypage.actions';
import SelectListComponent from '../_components/SelectListComponent';
import DateTimeComponent from 'react-datetime';
import moment from 'moment';
import styles from './css/pages.css';


class MyLeave extends React.Component{
    constructor(props){
        super(props);

        this.state = {
            loading: true,
            pages:0,
            page: 1,
            sizePerPage:10,
            status:1,
            pageOfItems: [],
            isFirst: true,
            bool:'',
            isDisplay: false,
            search:{},
            isSearch:false,
            selectName:'',
            sorted:{"id":"vacation_id","desc":true}
        };

        // this.onChangePage  = this.onChangePage.bind(this);
        this.openAdd = this.openAdd.bind(this);

    }

    componentDidMount() {
        console.log(this.state.selectName);
        if(this.props.match.params.status!=1){
            this.setState({isDisplay:false});
        }
        else this.setState({isDisplay:true});
        if(this.props.match.params.status==undefined){
            this.setState({isDisplay:true});
        }

    }
    componentWillUpdate(){

    }
    componentWillMount(status,bool) {
        // loading
        status = this.props.match.params.status;
        bool = this.props.match.params.is_approved;
        console.log('will mao '+status+"----"+bool);
        console.log("bac dai bang bang bang bang bang"+typeof bool+"--"+typeof status)
        if(bool===undefined&&status==='1'){
            console.log('will mao '+status+"----"+bool);
            this.setState({
                selectName:'New'
            })
        }else if(bool==='false'&&status==='2'){
            console.log('will mao '+status+"----"+bool);
            this.setState({
                selectName:'Approving'
            })
        }else if(bool==='true'&&status==='2'){
            console.log('will mao '+status+"----"+bool);
            this.setState({
                selectName:'Aprroved'
            })
        }else if(bool==='false'&&status==='-1'){
            console.log('will mao '+status+"----"+bool);
            this.setState({
                selectName:'Disaprroved'
            })
        }



        if(status){
            this.setState({
                status:status,
                bool:bool
            });
        }
        // console.log("bac dai bang"+this.props.match.params.is_approved);
    }


    fetchData(page,pageSize,sorted,bool,status){
        if(status === undefined) status = this.state.status;
        if(page === undefined) page =this.state.page;
        if(pageSize === undefined) pageSize = this.state.sizePerPage;
        if(bool === undefined) bool = this.state.bool;
        if(sorted === undefined) sorted = this.state.sorted;
        if(this.state.isFirst){
            this.props.dispatch(leaveActions.getLeaveInit(page,pageSize,sorted,bool,status));
            this.setState({isFirst: false})
        }else{
            this.props.dispatch(leaveActions.getLeaveByPage(page,pageSize,sorted,bool,status));
        }
    }
    searchData(page,pageSize,sorted,bool,status,search){
        this.setState({
            isSearch: true
        })
        if(status === undefined) status = this.state.status;
        if(page === undefined) page =this.state.page;
        if(pageSize === undefined) pageSize = this.state.sizePerPage;
        if(bool === undefined) bool = this.state.bool;
        if(search === undefined) search = this.state.search;
        if(sorted === undefined) sorted = this.state.sorted;
        this.props.dispatch(leaveActions.getLeaveBySearch(page,pageSize,sorted,bool,status,search));
        console.log(search);
    }
    onChangePage(page,sizePage) {
        this.setState({
            sizePerPage: sizePage
        })
        // alert(page);
        if(page){
            if(this.state.isSearch === false){
                this.fetchData(page,sizePage);
            }
            if(this.state.isSearch === true){
                this.searchData(page,sizePage);
            }
        }
    }

    openAdd(){
        let leave = {};
        if(this.props.projectListByEmp.length > 0){
            leave.project_id = this.props.projectListByEmp[0];
            // leave.projectName = this.props.projectList[0].name;
        }
        if(this.props.leaveTypeList.length > 0){
            leave.vacation_type = this.props.leaveTypeList[0].vacation_type_id;
            // overtime.otherType = this.props.overtimeTypeList[0].name;
        }
        this.setState({leave:leave});
        this.props.dispatch(mypageActions.openPopup());
    }


    handleDelete(value){
        console.log(value + " deleted");
        const options = {
            title: <i className="fa fa-trash" aria-hidden="true"></i>,
            message: 'Are you sure to delete this',
            buttons: [
              {
                label: 'Delete',
                onClick: () => this.props.dispatch(leaveActions.deleteLeave(value))
              },
              {
                label: 'Cancel'
              }
            ]
          };
          confirmAlert(options);
    }

    handleDelete2(value){
        console.log(value + " deleted");
        const options = {
            title: <i className="fa fa-trash" aria-hidden="true"></i>,
            message: 'Are you sure to delete this',
            buttons: [
              {
                label: 'Delete',
                onClick: () => this.props.dispatch(leaveActions.deleteLeave(value))
              },
              {
                label: 'Cancel'
              }
            ]
          };
          confirmAlert(options);
    }

    handleEdit(data){
        this.props.dispatch(mypageActions.openPopup());
        this.setState({
            leave:data
        })
    }

    handleEdit2(data){
        this.props.dispatch(mypageActions.openPopup());
        this.setState({
            leave:data
        })
    }

    convertProjectName(projectId) {
        var name;
        if(projectId){
          let {projectList} = this.props;
          if(projectList){
            for(var i = 0 ; i < projectList.length;i++){
              if(projectList[i].project_id == projectId){
                name = projectList[i].name;
                break;
              }
            }
          }
        }
        return name;
      }
    onStatusChange(e){
        let page;
        let pageSize;
        let status = [];
        let bool;
        let search;
        let sorted;
        if(Number(e.target.value)===0) //New
        {
                status=1;
                bool='';
                this.setState({isDisplay: true,status:status,bool:bool})
        }
        if(Number(e.target.value)===1) //Approving
        {
            for(var i=2;i<10;i++)
                status.push(i);
                bool=false;
                this.setState({isDisplay: false,status:status,bool:bool})
        }
        if(Number(e.target.value)===2) //Approved
        {
            for(var i=2;i<10;i++)
                status.push(i);
            bool=true;
            this.setState({isDisplay: false,status:status,bool:bool})
        }
        if(Number(e.target.value)===-1) // Disapproved
        {
                status=-1;
                bool=false;
                this.setState({isDisplay: false,status:status,bool:bool})
        }
        if(this.state.isSearch===false){this.fetchData(page,pageSize,sorted,bool,status);}
        if(this.state.isSearch===true){this.searchData(page,pageSize,sorted,bool,status,search);}

    }

    handleChange(e){
        e.preventDefault();
        const {name,value} = e.target;

        let search = this.state.search;
        if(value===''){
            search[name] = null;
        }else{search[name] = value;}
        this.setState({search:search});



    }

    handleStartDateChange(data) {
        let search = this.state.search;
        search["from_date"] = moment(data).format('DD/MM/YYYY hh:mm a');
        if(search["from_date"]==='Invalid date'){
            search["from_date"]=null;
         }
    this.setState({
        search:search
    });
    }
    handleEndDateChange(data){
        let search = this.state.search;
        search["to_date"] = moment(data).format('DD/MM/YYYY hh:mm a');
        if(search["to_date"]==='Invalid date'){
            search["to_date"]=null;
         }
        this.setState({
            search:search
        });
    }

    onSearchClick(e){
        e.preventDefault();
        let page;
        let pageSize;
        let status;
        let bool;
        let sorted;

        let search = this.state.search;
        this.searchData(page,pageSize,sorted,bool,status,search);
        console.log(this.state.search);
    }

    test(){
    }

    handleSort(sort){
        let page;
        let pageSize;
        let status;
        let bool;
        let search;
        this.setState(
            {
                sorted:sort
            }
        )
        if(this.state.isSearch===false){this.fetchData(page,pageSize,sort,bool,status);}
        if(this.state.isSearch===true){this.searchData(page,pageSize,sort,bool,status,search);}
        console.log(sort);
    }

    render(){
        const {leaveList,pages,loading,isOpen}  = this.props;
        const {projectList,leaveTypeList,projectListByEmp} = this.props;
        const {searchList,searchPages}=this.props
        const {pageOfItems,search} = this.state;
        let pageTmp = [];
        let no_data1='';
        let no_data2='';
        if(searchList!==undefined && leaveList===undefined){
            no_data1=null;
            no_data2='No Search Data';
        }
        if(searchList===undefined && leaveList!==undefined){
            no_data1='No Data';
            no_data2=null;
        }
        for(let i = 0 ;i < pages;i++){
            pageTmp.push(i+1)
        }
        let projectData = [];
        if(projectListByEmp){
            for(let i=0; i<projectListByEmp.length;i++){
                projectData.push({"name":this.convertProjectName(projectListByEmp[i]),"name":this.convertProjectName(projectListByEmp[i])});
            }
        }
        let typeData = [];
        if(leaveTypeList){
            for(let i=0; i<leaveTypeList.length;i++){
                typeData.push({"name":leaveTypeList[i].vacation_type_name,"value":leaveTypeList[i].vacation_type_name});

            }
        }
        let selectTypeData = [
            {
                "name":"New",
                "value":0
            },
            {
                "name":"Approving",
                "value":1
            },
            {
                "name":"Aprroved",
                "value":2
            },
            {
                "name":"Disaprroved",
                "value":-1
            }
        ]
        return(


            <div>
                <NavBar/>
                <Header/>
                <div className="right_col" role="main">
                    <h2 className={styles.text_Header}>My Leave Request</h2>
                    <div className="row">
                        <div className="col-md-6 col-xs-12">
                            {isOpen && <AddComponent leave={this.state.leave}/>}
                                    <Button type="button" btn="warning" onClick={this.openAdd}>Add New</Button>

                        </div>
                        <div className="col-md-6 col-xs-12">
                            <SelectListComponent style={{"width": "150px","float":"right"}} selectName={this.state.selectName} option={selectTypeData} onChange={this.onStatusChange.bind(this)}/>
                        </div>
                    </div>

                        <div className="row pull-center">
                            <form className={styles.style_search} onSubmit={this.onSearchClick.bind(this)}>
                                <div className="col-md-2 col-xs-12 form-group">
                                    <label>Project:</label><SelectListComponent option={projectData} value0="true" onChange={this.handleChange.bind(this)} name="pro_name" isAddEmpty="true"/>
                                </div>
                                <div className="col-md-2 col-xs-12 form-group">
                                    <label>Type:</label><SelectListComponent option={typeData} value0="true" onChange={this.handleChange.bind(this)} name="type_name" isAddEmpty="true"/>
                                </div>
                                <div className="col-md-2 col-xs-12 form-group">
                                    <label>From:</label><DateTimeComponent onChange={this.handleStartDateChange.bind(this)} value={search.from_date} closeOnSelect={true} name="from_date"/>
                                </div>
                                <div className="col-md-2 col-xs-12 form-group">
                                    <label>To:</label><DateTimeComponent onChange={this.handleEndDateChange.bind(this)} value={search.to_date} closeOnSelect={true} name="to_date"/>
                                </div>
                                <div className={"col-md-2 form-group "+ styles.btnSearch}>
                                    <br/>
                                    <Button type="submit" btn="primary"><span className="fa fa-search"></span>Search</Button>
                                </div>
                            </form>
                        </div>
                    <div style={{marginTop:"20px"}}>
                    {leaveList ?
                        <TableComponent isSTT={true} data = {convertData(leaveList,["Project","Type","From Date","To Date","Description","Next User Approved"],
                        ['name','vacation_type_name','from_date','to_date','description','next_approve_manager'],'vacation_id',['description','next_approve_manager'])}
                        isCrud={this.state.isDisplay} onDelete={this.handleDelete.bind(this)} onEdit={this.handleEdit.bind(this)} onSort={this.handleSort.bind(this)} sorted={this.state.sorted}/>:<h2>{no_data1}</h2>}

                        <Pagination items={leaveList} pages={pages} onChangePage={this.onChangePage.bind(this)}/>
                    {searchList ?
                        <TableComponent isSTT={true} data = {convertData(searchList,["Project","Type","From Date","To Date","Description","Next User Approved"],
                        ['name','vacation_type_name','from_date','to_date','description','next_approve_manager'],'vacation_id',['description','next_approve_manager'])}
                        isCrud={this.state.isDisplay} onDelete={this.handleDelete2.bind(this)} onEdit={this.handleEdit2.bind(this)}  onSort={this.handleSort.bind(this)} sorted={this.state.sorted}/>:<h2>{no_data2}</h2>}

                        <Pagination items={searchList} pages={searchPages} onChangePage={this.onChangePage.bind(this)}/>
                    </div>
                </div>
            </div>
        )
    }
}

function mapStateToProps(state) {
    const { leaveList,pages, loading,searchList,searchPages } = state.leaves;
    const { isOpen } = state.popup;
    const {projectList,leaveTypeList,projectListByEmp} = state.leaveCategory;

    return {
        leaveList,
        loading,
        pages,
        isOpen,
        projectList,
        leaveTypeList,
        projectListByEmp,
        searchList,
        searchPages
    };
}

const connectedLeave = connect(mapStateToProps)(MyLeave);
export { connectedLeave as MyLeave };
