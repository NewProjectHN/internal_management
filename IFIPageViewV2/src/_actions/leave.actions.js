import { leaveConstants } from '../_constants/leave.constants.js';
import { leaveService } from '../_services/leave.service.js';
import { alertActions } from './alert.actions.js';
import {leaveManagerConstants} from '../_constants/leaveManager.constants'

export const leaveActions = {
    getLeaveByPage,
    deleteLeave,
    getLeaveInit,
    saveLeave,
    editLeave,
    getEmployeeVacationByManager,
    performApprove,
    performUnApprove,
    getLeaveBySearch,
    getLeaveBySearchNeedApprove,
    getApprovedVacationLogByManager,
    getLeaveCategory,
    getDisApprovedVacationLogByManager,
    getLeaveBySearchApproved,
    getLeaveBySearchDisApproved
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
function getLeaveCategory(page,pageSize){
    return function(dispatch){
        return leaveService.getCategory().then(
            leaveCategory=>{
                dispatch(success(leaveCategory));
            },
            error =>dispatch(failure)
        )
    }
    function success(leaveCategory) {return {type: leaveConstants.GET_LEAVE_CATEGORY,leaveCategory}}
    function failure(err) { return {type: leaveConstants.GET_LEAVE_CATEGORY_FAILURE, err}}
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

function getLeaveBySearch(page,pageSize,sorted,bool,status,search){
    return dispatch => {
        dispatch(request());
        leaveService.getLeaveBySearch(page,pageSize,sorted,bool,status,search).then(
            leaveSearch => dispatch(success(leaveSearch)),
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: leaveConstants.SEARCH_LEAVE_REQUEST}}
    function success(leaveSearch) { return {type: leaveConstants.SEARCH_LEAVE_SUCCESS,leaveSearch}}
    function failure(error) { return {type: leaveConstants.SEARCH_LEAVE_FAILURE, error}}
}

function saveLeave(leaves) {
    return dispatch => {
        //dispatch(request());

        leaveService.saveLeave(leaves)
            .then(
                leaves =>{ 
                    dispatch(success(leaves));
                    dispatch(alertActions.success('Save successfully'));
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
                    dispatch(alertActions.success('Edit successfully'));
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
                dispatch(alertActions.success('Delete successfully'));
                
            },
            error => dispatch(failure(error))
        );
    }

    function request(){return {type: leaveConstants.DELETE_LEAVE_REQUEST}}
    function success(id){return {type: leaveConstants.DELETE_LEAVE_SUCCESS,id}}
    function failure(error){return {type: leaveConstants.DELETE_LEAVE_FAILURE, error}}
}


function getEmployeeVacationByManager(page, pageSize,sorted){
    return dispatch => {
        dispatch(request());
        leaveService.getEmployeeVacationByManager(page,pageSize,sorted).then(
            leaveEmp => {dispatch(success(leaveEmp))},
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: leaveManagerConstants.GETALL_REQUEST}}
    function success(leaveEmp) { return {type: leaveManagerConstants.GETALL_SUCCESS,leaveEmp}}
    function failure(error) { return {type: leaveManagerConstants.GETALL_FAILURE, error}}
}

function getApprovedVacationLogByManager(page,pageSize,sorted){
    return dispatch =>{
        dispatch(request());
        leaveService.getApprovedVacationLogByManager(page,pageSize,sorted).then(
            leaveEmp =>{dispatch(success(leaveEmp))},
            error => dispatch(failure(error))
        );
    };

    function request(){return {type:leaveManagerConstants.GETALL_APPROVED_REQUEST}}
    function success(leaveEmp){return {type:leaveManagerConstants.GETALL_APPROVED_SUCCESS,leaveEmp}}
    function failure(error){return {type:leaveManagerConstants.GETALL_APPROVED_FAILURE,error}}
}

function getDisApprovedVacationLogByManager(page,pageSize,sorted){
    return dispatch =>{
        dispatch(request());
        leaveService.getDisApprovedVacationLogByManager(page,pageSize,sorted).then(
            leaveEmp =>{dispatch(success(leaveEmp))},
            error => dispatch(failure(error))
        );
    };

    function request(){return {type:leaveManagerConstants.GETALL_DISAPPROVED_REQUEST}}
    function success(leaveEmp){return {type:leaveManagerConstants.GETALL_DISAPPROVED_SUCCESS,leaveEmp}}
    function failure(error){return {type:leaveManagerConstants.GETALL_DISAPPROVED_FAILURE,error}}
}
function getLeaveBySearchNeedApprove(page,pageSize,sorted,bool,search){
    return dispatch => {
        dispatch(request());
        leaveService.getLeaveBySearchNeedApprove(page,pageSize,sorted,bool,search).then(
            empSearch => dispatch(success(empSearch)),
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: leaveManagerConstants.SEARCH_LEAVE_REQUEST}}
    function success(empSearch) { return {type: leaveManagerConstants.SEARCH_LEAVE_SUCCESS,empSearch}}
    function failure(error) { return {type: leaveManagerConstants.SEARCH_LEAVE_FAILURE, error}}
}

function getLeaveBySearchApproved(page,pageSize,sorted,bool,search){
    return dispatch => {
        dispatch(request());
        leaveService.getLeaveBySearchApproved(page,pageSize,sorted,bool,search).then(
            empSearch => dispatch(success(empSearch)),
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: leaveManagerConstants.SEARCH_LEAVE_REQUEST}}
    function success(empSearch) { return {type: leaveManagerConstants.SEARCH_LEAVE_SUCCESS,empSearch}}
    function failure(error) { return {type: leaveManagerConstants.SEARCH_LEAVE_FAILURE, error}}
}

function getLeaveBySearchDisApproved(page,pageSize,sorted,bool,search){
    return dispatch => {
        dispatch(request());
        leaveService.getLeaveBySearchDisApproved(page,pageSize,sorted,bool,search).then(
            empSearch => dispatch(success(empSearch)),
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: leaveManagerConstants.SEARCH_LEAVE_REQUEST}}
    function success(empSearch) { return {type: leaveManagerConstants.SEARCH_LEAVE_SUCCESS,empSearch}}
    function failure(error) { return {type: leaveManagerConstants.SEARCH_LEAVE_FAILURE, error}}
}

function performApprove(id){
    return dispatch => {
        leaveService.performApprove(id).then(
            leaveEmpApprove => {dispatch(success(leaveEmpApprove));
                dispatch(alertActions.success('Approved Successfully'))},
            error => dispatch(failure(error))
        )
    }
    function request() {return  {type: leaveManagerConstants.APPROVE_REQUEST}}
    function success(leaveEmpApprove) { return {type: leaveManagerConstants.APPROVE_SUCCESS,leaveEmpApprove}}
    function failure(error) { return {type: leaveManagerConstants.APPROVE_FAILURE, error}}
}

function performUnApprove(id){
    return dispatch => {
   
        leaveService.performUnApprove(id).then(
            leaveEmpUnApprove => {dispatch(success(leaveEmpUnApprove));
                dispatch(alertActions.success('UnApproved Successfully'))},
            error => dispatch(failure(error))
        )
    }
    function request() {return  {type: leaveManagerConstants.UNAPPROVE_REQUEST}}
    function success(leaveEmpUnApprove) { return {type: leaveManagerConstants.UNAPPROVE_SUCCESS,leaveEmpUnApprove}}
    function failure(error) { return {type: leaveManagerConstants.UNAPPROVE_FAILURE, error}}
}

