import React from 'react';
import {connect} from 'react-redux';
import InputComponent from '../InputComponent';
import SelectComponent from '../SelectListComponent';
import Button from '../Button';
import {mypageActions} from '../../_actions/mypage.actions';
import DateTimeComponent from 'react-datetime';
import moment from 'moment';
import {leaveActions} from '../../_actions/leave.actions';


class AddComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            leave: this.props.leave,
            startDateClone: this.props.leave.from_date,
            endDateClone: this.props.leave.to_date,
            showReason: false,
            validButton: false,
            from_date: '',
            to_date: '',
            errorEndDate: false,
            errorStartDate: false,
            validForm: true,
            idProject: [],
            stDate: null,
            edDate: null,
            testId: 0,
            startAndEndDate: false,
            edited_project: []


        }
        this.closepopup = this.closepopup.bind(this);
        this.addLeave = this.addLeave.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleStartDateChange = this.handleStartDateChange.bind(this);
        this.handleEndDateChange = this.handleEndDateChange.bind(this);
    }

    closepopup() {
        this.props.dispatch(mypageActions.closePopup());
    }

    convertProjectName(projectId) {
        var name;
        if (projectId) {
            let {projectList} = this.props;
            if(projectList){
              for (var i = 0; i < projectList.length; i++) {
                  if (projectList[i].project_id == projectId) {
                      name = projectList[i].name;
                      break;
                  }
              }
            }
        }
        return name;
    }

    addLeave(event) {
        event.preventDefault();

        const {leave} = this.state;
        const {leaveList} = this.props;
        const {searchList} = this.props;
        //kiem tra neu k chon id thi lay id =0
        if (searchList === undefined) {
            if (leaveList == null) {
                this.props.dispatch(leaveActions.saveLeave(leave));
                this.props.dispatch(mypageActions.closePopup());
            } else if (leaveList != undefined && leaveList != null) {
                for (var i = 0; i < leaveList.length; i++) {
                    if (leave.vacation_id == leaveList[i].vacation_id) {
                        var update = true;
                        break;
                    } else update = false;
                }
                if (update == true) {
                    this.props.dispatch(leaveActions.editLeave(leave));
                    this.props.dispatch(mypageActions.closePopup());
                } else {
                    this.props.dispatch(leaveActions.saveLeave(leave));
                    this.props.dispatch(mypageActions.closePopup());
                }
            }
        } else {
            if (searchList == null) {
                this.props.dispatch(leaveActions.saveLeave(leave));
                this.props.dispatch(mypageActions.closePopup());
            } else if (searchList != undefined && searchList != null) {
                for (var i = 0; i < searchList.length; i++) {
                    if (leave.vacation_id == searchList[i].vacation_id) {
                        var update = true;
                        break;
                    } else update = false;
                }
                if (update == true) {
                    this.props.dispatch(leaveActions.editLeave(leave));
                    this.props.dispatch(mypageActions.closePopup());
                } else {
                    this.props.dispatch(leaveActions.saveLeave(leave));
                    this.props.dispatch(mypageActions.closePopup());
                }
            }
        }
    }

    handleChange(e) {
        const {name, value} = e.target;

        let leave = this.state.leave;

        leave[name] = value;
        this.setState({leave: leave});

        // if (leave.vacation_type == 1) {
        //     leave["description"] = "";
        //     this.setState({showReason: false});
        // } else
        //     this.setState({showReason: true});

        let project = this.props.projectList;

        var idProject = this.state.idProject;
        //kiem tra neu k chon thi id project==0
        //du cac truong dl bat buoc thi validForm==true
        const {leaveList} = this.props;
        const {searchList} = this.props;
        if (searchList === undefined) {
            for (var i = 0; i < leaveList.length; i++) {
                if (leave.vacation_id == leaveList[i].vacation_id) {
                    var arrKey = [leave["project_id"], leave["vacation_type"], leave["description"]];
                    var count = 0;
                    for (var i = 0; i < arrKey.length; i++) {
                        console.log(arrKey[i]);
                        if (arrKey[i] != "")
                            count++;

                    }
                    if (count >= 2)
                        this.setState({validForm: true});
                    console.log(this.state.validForm);
                    return;
                }


            }
            if (leave["project_id"] != null) {
                for (var i = 0; i < project.length; i++) {
                    if (project[i].project_id == leave["project_id"]) {

                        idProject.push(project[i]);


                    }
                }
            }
            else {
                idProject.push(project[0]);

            }
            console.log(idProject);
            this.setState({testId: leave["project_id"]});
        }
        else {
            for (var i = 0; i < searchList.length; i++) {
                if (leave.vacation_id == searchList[i].vacation_id) {
                    var arrKey = [leave["project_id"], leave["vacation_type"], leave["description"]];
                    var count = 0;
                    for (var i = 0; i < arrKey.length; i++) {
                        console.log(arrKey[i]);
                        if (arrKey[i] != "")
                            count++;

                    }
                    if (count >= 2)
                        this.setState({validForm: true});
                    console.log(this.state.validForm);
                    return;
                }


            }
            if (leave["project_id"] != null) {
                for (var i = 0; i < project.length; i++) {
                    if (project[i].project_id == leave["project_id"]) {

                        idProject.push(project[i]);


                    }
                }
            }
            else {
                idProject.push(project[0]);

            }
            console.log(idProject);
            this.setState({testId: leave["project_id"]});
        }


    }

    validateDate() {
        let {from_date, to_date} = this.state.leave;
        this.setState({validForm: false});
        if (from_date != null && from_date != '' && to_date != null && to_date != '') {
            var from_date_m = moment(from_date, 'DD/MM/YYYY hh:mm a');
            var to_date_m = moment(to_date, 'DD/MM/YYYY hh:mm a');
            if (from_date_m.isAfter(to_date_m)) {
                this.setState({startAndEndDate: true});
                this.setState({validForm: false});
            } else {
                this.setState({startAndEndDate: false});
                this.setState({validForm: true});
            }
        }
    }

    handleStartDateChange(data) {
        let leave = this.state.leave;
        try {
            leave["from_date"] = data.format('DD/MM/YYYY hh:mm a');
        } catch (e) {

        }
        this.validateDate();
    }

    handleEndDateChange(data) {
        let leave = this.state.leave;
        try {
            leave["to_date"] = data.format('DD/MM/YYYY hh:mm a');
        } catch (e) {

        }
        this.validateDate();
    }

    render() {
        const {leave} = this.props;
        const {projectListByEmp, leaveTypeList, isDone, leaveList, projectList} = this.props;
        let {showReason, validButton, errorEndDate, errorStartDate, validForm, startAndEndDate, edited_project} = this.state;
        let projectData = [];
        let start_date_moment;
        let end_date_moment;
        let {from_date, to_date} = this.state.leave;

        if (from_date != null) {
            start_date_moment = moment(from_date, 'DD/MM/YYYY hh:mm a');
        } else {
            // Khoi tao la 8h45 ngay hien tai
            var now = moment().format('DD/MM/YYYY');
            now = now + ' 08:45 am';
            start_date_moment = moment(now, 'DD/MM/YYYY hh:mm a');
            leave["from_date"] = moment(start_date_moment).format('DD/MM/YYYY hh:mm a');
        }
        if (to_date != null) {
            end_date_moment = moment(to_date, 'DD/MM/YYYY hh:mm a');
        } else {
            var now = moment().format('DD/MM/YYYY');
            now = now + ' 06:00 pm';
            end_date_moment = moment(now, 'DD/MM/YYYY hh:mm a');
            leave["to_date"] = moment(end_date_moment).format('DD/MM/YYYY hh:mm a');
        }
        if (projectListByEmp) {
            for (let i = 0; i < projectListByEmp.length; i++) {
                projectData.push({"name": this.convertProjectName(projectListByEmp[i]), "value": projectListByEmp[i]});
            }
        }
        let typeData = [];
        if (leaveTypeList) {
            for (let i = 0; i < leaveTypeList.length; i++) {
                typeData.push({
                    "name": leaveTypeList[i].vacation_type_name,
                    "value": leaveTypeList[i].vacation_type_id
                });

            }
        }

        return (
            <div className="popup-mask">
                <div className="container-register-content col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div className="panel panel-primary">
                        <div className="panel-heading">
                            <h3 className="panel-title text-center">Vacation</h3>
                        </div>
                        <div className="panel-body">
                            <form onSubmit={this.addLeave}>
                                <div className="form-group">
                                    <label>Project :</label>
                                    <SelectComponent option={projectData} valueSelect={leave.project_id}
                                                     onChange={this.handleChange} name="project_id"/>

                                </div>
                                <div className="form-group">
                                    <label>From :</label>
                                    <DateTimeComponent onChange={this.handleStartDateChange}
                                                       closeOnSelect={true} name="from_date" value={start_date_moment}
                                                       dateFormat="DD/MM/YYYY"/>
                                    {errorStartDate &&
                                    <div style={{color: "red"}}>start date wrong! Project
                                        start:{moment(edited_project[0].start_date).format('DD/MM/YYYY')}</div>
                                    }
                                </div>
                                <div className="form-group">
                                    <label>To :</label>
                                    <DateTimeComponent onChange={this.handleEndDateChange}
                                                       closeOnSelect={true} name="to_date" value={end_date_moment}
                                                       dateFormat="DD/MM/YYYY"/>
                                    {errorEndDate &&
                                    <div style={{color: "red"}}>End date wrong! Project
                                        end:{moment(edited_project[0].end_date).format('DD/MM/YYYY')} </div>
                                    }
                                    {startAndEndDate &&
                                    <div style={{color: "red"}}>End date is less than start date!</div>
                                    }
                                </div>
                                <div className="form-group">
                                    <label>Type :</label>
                                    <SelectComponent option={typeData} valueSelect={leave.vacation_type}
                                                     onChange={this.handleChange} name="vacation_type"/>

                                </div>

                                <div className="form-group">
                                    <label>Description :</label>
                                    <textarea className="form-control" rows="5" value={leave.description}
                                              onChange={this.handleChange} name="description"></textarea>
                                </div>


                                <br/>
                                <div className="text-center">
                                    {validForm &&
                                    <Button id="submit" type="submit" value="submit"
                                            btn="primary">{leave.vacation_id != undefined ? "Update" : "Add"}</Button>
                                    }
                                    {!validForm &&
                                    <Button id="submit" type="submit" value="submit" btn="primary"
                                            disabled>{leave.vacation_id != undefined ? "Update" : "Add"}</Button>
                                    }

                                    <Button type="button" btn="warning" onClick={this.closepopup}>Cancel</Button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

function mapStateToProps(state) {
    const {projectList, leaveTypeList, projectListByEmp} = state.leaveCategory;
    const {isDone, leaveList, searchList} = state.leaves;

    return {
        projectList,
        leaveTypeList,
        projectListByEmp,
        isDone,
        leaveList,
        searchList
    };
}

const connectedAdd = connect(mapStateToProps)(AddComponent);
export {connectedAdd as AddComponent};
