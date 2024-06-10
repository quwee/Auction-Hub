import api from "../api";

export default class UserService {

    static async changeDetails(changeDetailsRequest) {
        const op = 'UserService:changeDetails:'
        console.log(op)
        try {
            return await api.post('users/change-details', changeDetailsRequest)
        } catch (error) {
            console.log(`${op} error: `, error)
        }
    }

}
