import React from 'react';
import {connect} from 'react-redux';
import InputComponent from '../InputComponent';
import SelectComponent from '../SelectListComponent';
import Button from '../Button';
import {mypageActions} from '../../_actions/mypage.actions';
import DateTimeComponent from 'react-datetime';
import moment from 'moment';
import {overtimeActions} from '../../_actions/overtime.actions';


class AddOvertimeComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            overtime: this.props.overtime,
            startDateClone: this.props.overtime.from_hour,
            endDateClone: this.props.overtime.to_hour,
            showReason: false,
            validButton: false,
            from_hour: '',
            to_hour: '',
            errorEndDate: false,
            errorStartDate: false,
            validForm: true,
            startAndEndDate: false,
            idProject: [],
            stDate: null,
            edDate: null,
            testId: 0,
            edited_project: []
        }
        this.closepopup = this.closepopup.bind(this);
        this.addOvertime = this.addOvertime.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleStartHourChange = this.handleStartHourChange.bind(this);
        this.handleEndHourChange = this.handleEndHourChange.bind(this);
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

    addOvertime(event) {
        event.preventDefault();
        // this.state.overtime.otDate = this.state.startDateClone;
        const {overtime} = this.state;
        const {overtimeList} = this.props;
        const {searchList} = this.props;
        if (searchList === undefined) {
            if (overtimeList == null || overtimeList == '') {
                this.props.dispatch(overtimeActions.saveOvertime(overtime));
                this.props.dispatch(mypageActions.closePopup());
            } else if (overtimeList != undefined && overtimeList != null) {
                for (var i = 0; i < overtimeList.length; i++) {
                    if (overtime.overtime_id == overtimeList[i].overtime_id) {
                        var update = true;
                        break;

                    }
                    else update = false;

                }
                if (update == true) {
                    this.props.dispatch(overtimeActions.editOvertime(overtime));
                    this.props.dispatch(mypageActions.closePopup());
                } else {
                    this.props.dispatch(overtimeActions.saveOvertime(overtime));
                    this.props.dispatch(mypageActions.closePopup());
                }
            }
        }
        else {
            if (searchList == null) {
                this.props.dispatch(overtimeActions.saveOvertime(overtime));
                this.props.dispatch(mypageActions.closePopup());
            } else if (searchList != undefined && searchList != null) {
                for (var i = 0; i < searchList.length; i++) {
                    if (overtime.overtime_id == searchList[i].overtime_id) {
                        var update = true;
                        break;

                    }
                    else update = false;

                }
                if (update == true) {
                    this.props.dispatch(overtimeActions.editOvertime(overtime));
                    this.props.dispatch(mypageActions.closePopup());
                } else {
                    this.props.dispatch(overtimeActions.saveOvertime(overtime));
                    this.props.dispatch(mypageActions.closePopup());
                }
            }
        }

    }

    handleChange(e) {
        const {name, value} = e.target;

        let overtime = this.state.overtime;

        overtime[name] = value;
        this.setState({overtime: overtime});

        // if (overtime.overtime_type == 1) {
        //     overtime["description"] = "";
        //     this.setState({showReason: false});
        // }
        // else
        //     this.setState({showReason: true});
        let project = this.props.projectList;

        var idProject = this.state.idProject;
        //kiem tra neu k chon thi id project==0
        //du cac truong dl bat buoc thi validForm==true
        const {overtimeList} = this.props;
        const {searchList} = this.props;
        if (searchList === undefined) {
            for (var i = 0; i < overtimeList.length; i++) {
                if (overtime.overtime_id == overtimeList[i].overtime_id) {
                    var arrKey = [overtime["project_id"], overtime["overtime_type"], overtime["description"]];
                    var count = 0;
                    for (var i = 0; i < arrKey.length; i++) {
                        console.log(arrKey[i]);
                        if (arrKey[i] != "")
                            count++;

                    }
                    if (count >= 2)
                        this.setState({validForm: true});

                    return;
                }


            }
            if (overtime["project_id"] != null) {
                for (var i = 0; i < project.length; i++) {
                    if (project[i].project_id == overtime["project_id"]) {

                        idProject.push(project[i]);


                    }
                }
            }
            else {
                idProject.push(project[0]);

            }
            console.log(idProject);
            this.setState({testId: overtime["project_id"]});
        }
        else {
            for (var i = 0; i < searchList.length; i++) {
                if (overtime.overtime_id == searchList[i].overtime_id) {
                    var arrKey = [overtime["project_id"], overtime["overtime_type"], overtime["description"]];
                    var count = 0;
                    for (var i = 0; i < arrKey.length; i++) {
                        console.log(arrKey[i]);
                        if (arrKey[i] != "")
                            count++;

                    }
                    if (count >= 2)
                        this.setState({validForm: true});

                    return;
                }


            }
            if (overtime["project_id"] != null) {
                for (var i = 0; i < project.length; i++) {
                    if (project[i].project_id == overtime["project_id"]) {

                        idProject.push(project[i]);


                    }
                }
            }
            else {
                idProject.push(project[0]);

            }
            console.log(idProject);
            this.setState({testId: overtime["project_id"]});
        }
    }

    validateDate() {
        let from_date = this.state.overtime.from_hour;
        let to_date = this.state.overtime.to_hour;

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

    handleStartHourChange(data) {
        let overtime = this.state.overtime;
        try {
            overtime["from_hour"] = data.format('DD/MM/YYYY hh:mm a');
        }catch(e){

        }
        this.validateDate();
    }

    handleEndHourChange(data) {
        let overtime = this.state.overtime;
        try {
            overtime["to_hour"] = data.format('DD/MM/YYYY hh:mm a');
        }catch(e){
        }
        this.validateDate();
    }

    render() {
        const {overtime} = this.props;
        const {projectListByEmp, overtimeTypeList, isDone, overtimeList, projectList} = this.props;
        let {showReason, validButton, errorEndDate, errorStartDate, validForm, startAndEndDate, edited_project} = this.state;
        let projectData = [];
        if (projectListByEmp) {
            for (let i = 0; i < projectListByEmp.length; i++) {
                projectData.push({"name": this.convertProjectName(projectListByEmp[i]), "value": projectListByEmp[i]});
            }
        }
        let typeData = [];
        if (overtimeTypeList) {
            for (let i = 0; i < overtimeTypeList.length; i++) {
                typeData.push({
                    "name": overtimeTypeList[i].overtime_type_name,
                    "value": overtimeTypeList[i].overtime_type_id
                });

            }
        }

        let start_date_moment = moment();
        let end_date_moment = moment();
        let {from_hour, to_hour} = this.state.overtime;
        if (from_hour != null) {
            start_date_moment = moment(from_hour, 'DD/MM/YYYY hh:mm a');
        }else{
            var now = moment().format('DD/MM/YYYY');
            now = now + ' 06:00 pm';
            start_date_moment = moment(now,'DD/MM/YYYY hh:mm a');
            overtime["from_hour"] = moment(start_date_moment).format('DD/MM/YYYY hh:mm a');
        }
        if (to_hour != null) {
            end_date_moment = moment(to_hour, 'DD/MM/YYYY hh:mm a');
        }else{
            var now = moment().format('DD/MM/YYYY');
            now = now + ' 08:00 pm';
            end_date_moment = moment(now,'DD/MM/YYYY hh:mm a');
            overtime["to_hour"] = moment(end_date_moment).format('DD/MM/YYYY hh:mm a');
        }

        return (
            <div className="popup-mask">
                <div className="container-register-content col-xs-8 col-sm-8 col-md-8 col-lg-8">
                    <div className="panel panel-primary">
                        <div className="panel-heading">
                            <h3 className="panel-title text-center">Overtime</h3>
                        </div>
                        <div className="panel-body">
                            <form onSubmit={this.addOvertime}>
                                <div className="form-group">
                                    <label>Project :</label>
                                    <SelectComponent option={projectData} valueSelect={overtime.project_id}
                                                     onChange={this.handleChange} name="project_id"/>

                                </div>
                                <div className="form-group">
                                    <label>From :</label>
                                    <DateTimeComponent onChange={this.handleStartHourChange} value={overtime.from_hour}
                                                       closeOnSelect={true} name="from_hour" dateFormat="DD/MM/YYYY"/>
                                    {errorStartDate &&
                                    <div style={{color: "red"}}>start date wrong! Project
                                        start:{moment(edited_project[0].start_date).format('DD/MM/YYYY')}</div>
                                    }
                                </div>
                                <div className="form-group">
                                    <label>To :</label>
                                    <DateTimeComponent onChange={this.handleEndHourChange} value={overtime.to_hour}
                                                       closeOnSelect={true} name="to_hour" dateFormat="DD/MM/YYYY"/>
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
                                    <SelectComponent option={typeData} valueSelect={overtime.overtime_type}
                                                     onChange={this.handleChange} name="overtime_type"/>

                                </div>

                                    <div className="form-group">
                                        <label>Description :</label>
                                        <textarea className="form-control" rows="5" value={overtime.description}
                                                  onChange={this.handleChange} name="description"></textarea>
                                    </div>


                                <br/>
                                <div className="text-center">
                                    {validForm &&
                                    <Button id="submit" type="submit" value="submit"
                                            btn="primary">{overtime.overtime_id != undefined ? "Update" : "Add"}</Button>
                                    }
                                    {!validForm &&
                                    <Button id="submit" type="submit" value="submit" btn="primary"
                                            disabled>{overtime.overtime_id != undefined ? "Update" : "Add"}</Button>
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
    const {projectList, overtimeTypeList, projectListByEmp} = state.overtimeCategory;
    const {isDone, overtimeList, searchList} = state.overtimes;

    return {
        projectList,
        overtimeTypeList,
        projectListByEmp,
        isDone,
        overtimeList,
        searchList
    };
}

const connectedAdd = connect(mapStateToProps)(AddOvertimeComponent);
export {connectedAdd as AddOvertimeComponent};
