import ndjson from "fetch-ndjson";

export default class DataUtils{

    /**
     * Method add instance from response list to messages array.
     * @param response from API with messages
     * @param messages array which contain  messages
     */
    static async addDataToArray(response: Response, messages: [any]) {
        let reader = response?.body?.getReader();

        if (reader == null) return;

        let gen = ndjson(reader);
        let counter = true;
        while (counter) {
            let {done, value} = await gen.next();
            if (done) {
                break;
            }
            let jsonValue = JSON.parse(JSON.stringify(value));
            messages.push(jsonValue);
        }
    }
}
