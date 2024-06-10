import React from 'react';
import cl from "../../../styles/RightSide.module.css";
import Bids from "./bid/Bids";
import Chat from "./chat/Chat";

const RightSide = ({messages, sendMessageCallback, placeBidCallback, completeCallback, auctionDetails, auctionComplete, showToolTipCallBack}) => {
    return (
        <div className={cl.right}>
            <Bids
                auctionDetails={auctionDetails}
                placeBidCallback={placeBidCallback}
                auctionComplete={auctionComplete}
                completeCallback={completeCallback}
                showToolTipCallBack={showToolTipCallBack}/>
            <Chat
                messages={messages}
                sendMessageCallback={sendMessageCallback}
                auctionComplete={auctionComplete}
                showToolTipCallBack={showToolTipCallBack}/>
        </div>
    );
};

export default RightSide;