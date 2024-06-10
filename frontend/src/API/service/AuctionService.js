import axios from "axios";
import api, {API_URL} from "../api";

export default class AuctionService {

    static async createAuction(auctionCreateRequestDto) {
        const op = 'AuctionService:createAuction:'
        console.log(op)
        try {
            return await api.post('auction/create-auction', auctionCreateRequestDto)
        } catch (error) {
            console.log(`${op} error: `, error)
        }
    }

    static async getAllActive() {
        const op = 'AuctionService:getAllActive:'
        console.log(op)
        try {
            return await api.get('auction')
        } catch (error) {
            console.log(`${op} error: `, error)
        }
    }

    static async getAuctionDetails(id) {
        const op = 'AuctionService:getAllActive:'
        console.log(op)
        try {
            return await api.get(`auction/details/${id}`)
        } catch (error) {
            console.log(`${op} error: `, error)
        }
    }

    static async loadImage(imageName) {
        const op = 'AuctionService:loadImage:'
        console.log(op)
        try {
            return await api.get(`auction/load-image/${imageName}`)
        } catch (error) {
            console.log(`${op} error: `, error)
        }
    }

    static async complete(completeDto) {
        const op = 'AuctionService:complete:'
        console.log(op)
        try {
            return await api.post('auction/complete-auction', completeDto)
        } catch (error) {
            console.log(`${op} error: `, error)
        }
    }

}