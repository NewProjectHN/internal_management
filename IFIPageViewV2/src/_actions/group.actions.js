import { groupConstants } from '../_constants/group.constants';
import { GroupService } from '../_services/group.service';

export const groupActions = {
    getGroup
};

function getGroup(){
    return dispatch => {
        dispatch(request());
        GroupService.getGroup().then(
            groupBean =>dispatch(success(groupBean)),
            error=>dispatch(failure(error))
        );
    };
        function request() {return {type: groupConstants.GETALL_REQUEST_GROUP}}
        function success(groupBean) {return{type:groupConstants.GETALL_SUCCESS_GROUP, groupBean}}
        function failure(error) {return {type:groupConstants.GETALL_FAILURE_GROUP, error}}
}

