import { leaveConstants } from '../_constants/leave.constants.js';
import { leaveService } from '../_services/leave.service.js';
import { alertActions } from './alert.actions.js';
import {leaveManagerConstants} from '../_constants/leaveManager.constants'
import { overtimeService } from '../_services/overtime.service.js';
import { overtimeConstants } from '../_constants/overtime.constants.js';

export const overtimeActions = {
    getLeaveByPage,
    deleteLeave,
    getLeaveInit,
    saveLeave,
    editLeave,
    getEmployeeOvertimeByManager,
    performApprove,
    performUnApprove
};

function getLeaveByPage(page,pageSize,sorted,bool,status){
    return dispatch => {
        dispatch(request());
        leaveService.getLeaveByPage(page,pageSize,sorted,bool,status).then(
            leaveBean => dispatch(success(leaveBean)),
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: leaveConstants.GETALL_REQUEST}}
    function success(leaveBean) { return {type: leaveConstants.GETALL_SUCCESS,leaveBean}}
    function failure(error) { return {type: leaveConstants.GETALL_FAILURE, error}}
}

function getLeaveInit(page,pageSize,sorted,bool,status){
    return function(dispatch){
        return leaveService.getCategory().then(
            leaveCategory => {
                dispatch(success(leaveCategory));
                dispatch(getLeaveByPage(page,pageSize,sorted,bool,status));
            },
            error => dispatch(failure(error))
        );
    };
    function success(leaveCategory) {return {type: leaveConstants.GET_LEAVE_CATEGORY,leaveCategory}}
    function failure(err) { return {type: leaveConstants.GET_LEAVE_CATEGORY_FAILURE, err}}
}

function saveLeave(leaves) {
    return dispatch => {
        //dispatch(request());

        leaveService.saveLeave(leaves)
            .then(
                leaves =>{ 
                    dispatch(success(leaves));
                    },
                //error => dispatch(failure(error))
            );
    };

    //function request() { return { type: overtimeConstants.GETALL_REQUEST } }
    function success(leaves) {
      return { type: leaveConstants.SAVE_SUCCESS, leaves }
    }
    //function failure(error) { return { type: overtimeConstants.GETALL_FAILURE, error } }
}

function editLeave(leave){
    return dispatch => {
        leaveService.editLeave(leave)
            .then(
                leave =>{
                    dispatch(success(leave));
                    
                },
            );
    };
    function success(leave){
        return {type: leaveConstants.EDIT_SUCCESS, leave}
    }

}
function deleteLeave(id){
    return dispatch => {
        dispatch(request());
        leaveService.deleteLeave(id).then(
            () => {
                dispatch(success(id));
                
            },
            error => dispatch(failure(error))
        );
    }

    function request(){return {type: leaveConstants.DELETE_LEAVE_REQUEST}}
    function success(id){return {type: leaveConstants.DELETE_LEAVE_SUCCESS,id}}
    function failure(error){return {type: leaveConstants.DELETE_LEAVE_FAILURE, error}}
}


function getEmployeeOvertimeByManager(page, pageSize,sorted){
    return dispatch => {
        dispatch(request());
        overtimeService.getEmployeeOvertimeByManager(page,pageSize,sorted).then(
            overtimeEmp => {dispatch(success(overtimeEmp))},
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: overtimeConstants.GETALL_REQUEST}}
    function success(overtimeEmp) { return {type: overtimeConstants.GETALL_SUCCESS,overtimeEmp}}
    function failure(error) { return {type: overtimeConstants.GETALL_FAILURE, error}}
}

function performApprove(id){
    return dispatch => {
        // dispatch(request());
        overtimeService.performApprove(id).then(
            overtimeEmpApprove => {dispatch(success(overtimeEmpApprove));
                dispatch(alertActions.success('Approved Successfully'))},
            error => dispatch(failure(error))
        )
    }
    function request() {return  {type: overtimeConstants.APPROVE_REQUEST}}
    function success(overtimeEmpApprove) { return {type: overtimeConstants.APPROVE_SUCCESS,overtimeEmpApprove}}
    function failure(error) { return {type: overtimeConstants.APPROVE_FAILURE, error}}
}

function performUnApprove(id){
    return dispatch => {
        // dispatch(request());
        overtimeService.performUnApprove(id).then(
            overtimeEmpUnApprove => {dispatch(success(overtimeEmpUnApprove));
                dispatch(alertActions.success('UnApproved Successfully'))},
            error => dispatch(failure(error))
        )
    }
    function request() {return  {type: overtimeConstants.UNAPPROVE_REQUEST}}
    function success(overtimeEmpUnApprove) { return {type: overtimeConstants.UNAPPROVE_SUCCESS,overtimeEmpUnApprove}}
    function failure(error) { return {type: overtimeConstants.UNAPPROVE_FAILURE, error}}
}

