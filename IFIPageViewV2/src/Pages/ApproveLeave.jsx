import React, { Component } from 'react';
import { connect } from 'react-redux';
import TableComponent from '../_components/TableComponent/TableComponent';
import convertData from '../_convertData/convertData';
import { leaveActions } from '../_actions/leave.actions';
import NavBar from '../TemplatePage/NavBar';
import Header from '../TemplatePage/Header';
import Button from '../_components/Button';
import Pagination from '../_components/PaginationComponentGoogle';
import SelectListComponent from '../_components/SelectListComponent';
import DateTimeComponent from 'react-datetime';
import moment from 'moment';
import styles from './css/pages.css';

class ApproveLeave extends React.Component{

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
            isDisplay: true,
            valueApprove: [],
            search:{},
            isSearch: false,
            is_checkbox:true,
            sorted:{"id":"v.vacation_id","desc":true}
        };
        this.handleChange= this.handleChange.bind(this);
        this.handleApprove=this.handleApprove.bind(this);
        this.handleApprove2=this.handleApprove2.bind(this);
        this.handleApproved=this.handleApproved.bind(this);
        this.handleUnApproved=this.handleUnApproved.bind(this);

    }

    onStatusChange(e){
        let page;
        let pageSize;
        let sorted;
        
        if(Number(e.target.value)===0) //Approve
        {
                
                this.setState({isDisplay: true,is_checkbox:true,bool:''});
                this.value2=0;
                console.log(this.value2);

        }
        if(Number(e.target.value)===1) //Approved
        {
            
                this.setState({isDisplay: false,is_checkbox:false,bool:true});
                this.value2=1;

                console.log(this.value2)
                

        }
        if(Number(e.target.value)===-1) // Disapproved
        {
            this.setState({isDisplay: false,is_checkbox:false,bool:false});
            this.value2=-1;
             
        }
        this.fetchData(page,pageSize,sorted);
        
    }
    componentWillMount() {
        this.value2=0;
    }
    fetchData(page,pageSize,sorted){
        if(page === undefined) page =this.state.page;
        if(pageSize === undefined) pageSize = this.state.sizePerPage;
        if(sorted === undefined) sorted = this.state.sorted;

        this.props.dispatch(leaveActions.getLeaveCategory(page,pageSize));
            if(this.value2===0)
            this.props.dispatch(leaveActions.getEmployeeVacationByManager(page,pageSize,sorted));
            else if(this.value2===1)
            this.props.dispatch(leaveActions.getApprovedVacationLogByManager(page,pageSize,sorted));
            else if(this.value2===-1)
            this.props.dispatch(leaveActions.getDisApprovedVacationLogByManager(page,pageSize,sorted));
        
    }
    searchData(page,pageSize,sorted,bool,search){
        this.setState({
            isSearch: true
        })
        if(page === undefined) page =this.state.page;
        if(pageSize === undefined) pageSize = this.state.sizePerPage;
        if(bool === undefined) bool = this.state.bool;
        if(search === undefined) search = this.state.search;
        if(sorted === undefined) sorted = this.state.sorted;
        
        if(this.value2===0)this.props.dispatch(leaveActions.getLeaveBySearchNeedApprove(page,pageSize,sorted,bool,search));
        if(this.value2===1)this.props.dispatch(leaveActions.getLeaveBySearchApproved(page,pageSize,sorted,bool,search));
        if(this.value2===-1)this.props.dispatch(leaveActions.getLeaveBySearchDisApproved(page,pageSize,sorted,bool,search));
    }
    onChangePage(page,sizePage) {
        // alert(page);
        this.setState({
            sizePerPage: sizePage
        })
        if(page){
            if(this.state.isSearch === false){
                this.fetchData(page,sizePage);
            }
            if(this.state.isSearch === true){
                this.searchData(page,sizePage);
            }
        }
    }

    handleApproved(data){
   
        if(data!==undefined){
        for(var i=0;i<data.length;i++){
            this.props.dispatch(leaveActions.performApprove(data[i]));
            
        }

    }
    
   
    
}
    handleApprove(data){
        if(data!==undefined){
        
        const value = [];
        for(var i=0;i<data.length;i++){
            // this.props.dispatch(leaveActions.performApprove(data[i]));
            value.push(data[i]);
        }
        this.setState({
            valueApprove: value,
        });
      }
    }
    handleApprove2(data){
        if(data!==undefined){
            this.setState({is_disable:false});
        const value = [];
        for(var i=0;i<data.length;i++){
            // this.props.dispatch(leaveActions.performApprove(data[i]));
            value.push(data[i]);
        }
        this.setState({
            valueApprove: value,
        });
    }
}
    handleUnApproved(data){
        if(data!==undefined){
          
        for(var i=0;i<data.length;i++){
            this.props.dispatch(leaveActions.performUnApprove(data[i]));
     
        }
    } 
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
        this.setState({
            search:search
        });
    }
    handleEndDateChange(data){
        let search = this.state.search;
        search["to_date"] = moment(data).format('DD/MM/YYYY hh:mm a');
        this.setState({
            search:search
        });
    }
    convertProjectName(projectId) {
        var name;
        if(projectId){
          for(var i = 0 ; i < this.props.projectList.length;i++){
            if(this.props.projectList[i].project_id == projectId){
              name = this.props.projectList[i].name;
              break;
            }
          }
        }
        return name;
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
        console.log(this.state.bool);
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
        if(this.state.isSearch===false){this.fetchData(page,pageSize,sort);}
        if(this.state.isSearch===true){this.searchData(page,pageSize,sort,bool,search);}
        console.log(sort);
    }

    render(){
        let name=''
        if(this.value2===0){
            name='Leave Request Require To Approve'
        }else if(this.value2===1){
            name='Leave Approved History'
        }
        else if(this.value2===-1){
            name='Leave DisApproved History'
        }
        const {leaveList,pages,loading,isOpen,leaveEmp,searchEmpList,searchEmpPages}  = this.props;
        const {projectList,leaveTypeList,projectListByEmp,projectListByManager} = this.props;
        const {search} = this.state;
        let no_data1='';
        let no_data2='';
        if(searchEmpList!==undefined && leaveEmp===undefined){
            no_data1=null;
            no_data2='No Search Data';
        }
        if(searchEmpList===undefined && leaveEmp!==undefined){
            no_data1='No Data';
            no_data2=null;
        }
        let projectData = [];
        if(projectListByManager){
            for(let i=0; i<projectListByManager.length;i++){
                projectData.push({"name":this.convertProjectName(projectListByManager[i]),"name":this.convertProjectName(projectListByManager[i])}); 
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
                "name":"Approve",
                "value":0
            },
            {
                "name":"Approved",
                "value":1
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
                <div  className="right_col" role="main">
                    <h2 className={styles.text_Header}>{name}</h2>
                    
                    <div className="row">
                        
                        <div className="col-md-6 pull-right">
                            {console.log(this.state.selectName)}
                            <SelectListComponent style={{"width": "150px","float":"right"}} selectName={this.state.selectName} option={selectTypeData} onChange={this.onStatusChange.bind(this)}/>
                        </div>
                    </div> 
                    <div className="row pull-center">
                        <form className={styles.style_search} onSubmit={this.onSearchClick.bind(this)}>
                        <div className="col-md-2 col-xs-12 form-group">
                                <label>Employee:</label><input type="text" className="form-control" name="emp_name" onChange={this.handleChange.bind(this)}/>
                            </div>
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
                    { this.state.is_checkbox? <Button type="button"  btn="warning" onClick={() => this.handleApproved(this.state.valueApprove)}>Approve</Button>:''}
                   { this.state.is_checkbox? <Button type="button"  btn="warning" onClick={() => this.handleUnApproved(this.state.valueApprove)}>Disapprove</Button>:''}
                    <div style={{marginTop:"20px"}}>
                    {leaveEmp ? 
                    <TableComponent isSTT={true} data = {convertData(leaveEmp,["Employee","Project","From Date","To Date","Type","Description"],['fullname','name','from_date','to_date','vacation_type_name','description'],'vacation_id',['description'])}
                    isCheckbox={this.state.is_checkbox} onApprove={this.handleApprove.bind(this)} onSort={this.handleSort.bind(this)} sorted={this.state.sorted}
                    />:<h2>{no_data1}</h2>}
                    <Pagination items={leaveEmp} pages={pages} onChangePage={this.onChangePage.bind(this)}/>
                    {searchEmpList ? 
                    <TableComponent isSTT={true} data = {convertData(searchEmpList,["Employee","Project","From Date","To Date","Type","Description"],['fullname','name','from_date','to_date','vacation_type_name','description'],'vacation_id',['description'])}
                    isCheckbox={this.state.is_checkbox} onApprove={this.handleApprove2.bind(this)} onSort={this.handleSort.bind(this)} sorted={this.state.sorted}
                    />:<h2>{no_data2}</h2>}
                    <Pagination items={searchEmpList} pages={searchEmpPages} onChangePage={this.onChangePage.bind(this)}/>
                    </div>
                    
                </div>
            </div>
        )
    }
}

function mapStateToProps(state) {
    const { leaveList,pages, loading,leaveEmp,searchEmpList,searchEmpPages,leaveEmp2,leaveEmp3 } = state.leaves;
    const { isOpen } = state.popup;
    const {projectList,leaveTypeList,projectListByEmp,projectListByManager} = state.leaveCategory;
  
    return {
        leaveList,
        loading,
        pages,
        isOpen,
        projectList,
        leaveTypeList,
        projectListByEmp,
        leaveEmp,
        leaveEmp2,
        leaveEmp3,
        searchEmpList,
        searchEmpPages,
        projectListByManager
    };
}

const connectedApproveLeave = connect(mapStateToProps)(ApproveLeave);
export { connectedApproveLeave as ApproveLeave };