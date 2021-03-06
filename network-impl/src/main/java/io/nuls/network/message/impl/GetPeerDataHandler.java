package io.nuls.network.message.impl;

import io.nuls.core.context.NulsContext;
import io.nuls.db.dao.PeerDao;
import io.nuls.network.entity.Peer;
import io.nuls.network.message.BaseNetworkData;
import io.nuls.network.message.NetworkDataResult;
import io.nuls.network.message.entity.GetPeerData;
import io.nuls.network.message.entity.PeerData;
import io.nuls.network.message.messageHandler.NetWorkDataHandler;
import io.nuls.network.service.impl.PeersManager;

import java.util.List;

/**
 * @author vivi
 * @date 2017/11/21
 */
public class GetPeerDataHandler implements NetWorkDataHandler {

    private static final GetPeerDataHandler INSTANCE = new GetPeerDataHandler();

    private PeersManager peersManager;

    private GetPeerDataHandler() {

    }

    public static GetPeerDataHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public NetworkDataResult process(BaseNetworkData message, Peer peer) {
        GetPeerData getPeerData = (GetPeerData) message;

        List<Peer> list = peersManager.getAvailablePeers(getPeerData.getLength(), peer);
        PeerData replyData = new PeerData();
        replyData.setPeers(list);
        return new NetworkDataResult(true, replyData);
    }

    public void setPeersManager(PeersManager peersManager) {
        this.peersManager = peersManager;
    }
}
