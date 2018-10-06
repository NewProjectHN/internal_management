import { homeConstants } from '../_constants/home.constants.js';
import { homeService } from '../_services/home.service.js';
import { alertActions } from './alert.actions.js';

export const homeActions = {
    getCount,
    getOvertimeCount,
    getCountNeedApproveManager
};

function getCount(){
    return dispatch => {
        dispatch(request());
        homeService.getCount().then(
            homeLeaveCount => dispatch(success(homeLeaveCount)),
            error => dispatch(failure(error))
        );
    };

    function request() {return  {type: homeConstants.GETALL_REQUEST}}
    function success(homeLeaveCount) { return {type: homeConstants.GETALL_SUCCESS,homeLeaveCount}}
    function failure(error) { return {type: homeConstants.GETALL_FAILURE, error}}
}

    function getOvertimeCount(){
        return dispatch => {
            dispatch(request());
            homeService.getOvertimeCount().then(
                homeOvertimeCount => dispatch(success(homeOvertimeCount)),
                error => dispatch(failure(error))
            )
        }

        function request() {return  {type: homeConstants.GETALL_OVERTIME_REQUEST}}
        function success(homeOvertimeCount) { return {type: homeConstants.GETALL_OVERTIME_SUCCESS,homeOvertimeCount}}
        function failure(error) { return {type: homeConstants.GETALL_OVERTIME_FAILURE, error}}
    }

    function getCountNeedApproveManager(){
        return dispatch=>{
            dispatch(request());
            homeService.getCountNeedApproveManager().then(
                homeCountNeedApproveManager => dispatch(success(homeCountNeedApproveManager)),
                error =>dispatch(failure(error))
            )
        }

        function request() {return  {type: homeConstants.GETALL_MANAGER_REQUEST}}
        function success(homeCountNeedApproveManager) { return {type: homeConstants.GETALL_MANAGER_SUCCESS,homeCountNeedApproveManager}}
        function failure(error) { return {type: homeConstants.GETALL_MANAGER_FAILURE, error}}
    }