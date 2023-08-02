const LOAD_MESSAGES_URL = 'http://localhost:8090/api/v1/message/get-messages?limit=100';
const DELETE_MESSAGE_URL = 'http://localhost:8090/api/v1/message/delete?messageId=';
const UPLOAD_FILE_MESSAGE_URL = 'http://localhost:8090/api/v1/file';
const CREATE_NEW_MESSAGE_URL = 'http://localhost:8090/api/v1/message/create';
const CREATE_NEW_MESSAGES_URL = 'http://localhost:8090/api/v1/message/create-messages';

export default class ApiUtils{
    /**
     * Method load first 100 message from backend.
     */
    static async loadMessages(){
        return await fetch(LOAD_MESSAGES_URL);
    }

    /**
     * Method delete message by id.
     * @param it message id
     */
    static async deleteMessage(it: String){
        return await fetch(DELETE_MESSAGE_URL + it, {method: 'DELETE'});
    }

    /**
     * Method upload file with messages.
     * @param file file with messages
     */
    static async uploadFile(file: File){
        let formData = new FormData()
        formData.append('file', file, file.name)

        return await fetch(UPLOAD_FILE_MESSAGE_URL, {
            method: "POST",
            body: formData
        });
    }

    /**
     * Method for create new message.
     */
    static async createMessage(
        first_name_field: String,
        last_name_field: String,
        age_field: Number,
        birth_date_field: String
    ){
        let request = {
            firstName: first_name_field,
            lastName: last_name_field,
            age: age_field,
            birthDate: birth_date_field
        };
        return await fetch(CREATE_NEW_MESSAGE_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
        });
    }

    /**
     * Method for update message.
     */
    static async updateMessage(
        id_field: String,
        first_name_field: String,
        last_name_field: String,
        age_field: Number,
        birth_date_field: String
    ){
        let request = {
            id: id_field,
            firstName: first_name_field,
            lastName: last_name_field,
            age: age_field,
            birthDate: birth_date_field
        };
        return await fetch(CREATE_NEW_MESSAGE_URL, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
        });
    }

    /**
     * Method for create new messages
     */
    static async createMessages(messages: any[]){
        let request = messages.map(it => {
            return {
                firstName: it.firstName,
                lastName: it.lastName,
                age: it.age,
                birthDate: it.birthDate
            }
        });
        return await fetch(CREATE_NEW_MESSAGES_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
        });
    }
}

