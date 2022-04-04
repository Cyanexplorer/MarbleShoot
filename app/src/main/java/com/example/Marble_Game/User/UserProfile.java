package com.example.Marble_Game.User;

/**
 * Created by g3863 on 2017/10/14.
 */

public class UserProfile {

    private UserState state;
    private UserAbi abi;
    private UserRecord record;
    private UpdateData updateData;

    private com.example.Marble_Game.UI.UpdateData update = new com.example.Marble_Game.UI.UpdateData() {
        @Override
        public void update() {
            updateData.update();
        }
    };

    public UserProfile(UserAbi abi, UserRecord record){
        this.abi = abi;
        this.record = record;
        state = new UserState(abi.getHp_max(),abi.getSp_max(),abi.getMb_max());
    }

    public static UserProfile stdProfile(){
        UserAbi abi = new UserAbi("Default",100,6,100,1,new Boolean[]{true});
        UserRecord record = new UserRecord(0,0,0);
        return new UserProfile(abi,record);
    }

    public UserAbi getAbi(){
        return abi;
    }
    public UserRecord getRecord(){
        return record;
    }
    public UserState getState(){
        return state;
    }

    public void setUpdateData(UpdateData updateData){
        this.updateData = updateData;
    }

    public void setRecord(int score){
        record.setCurrentScore(record.getCurrentScore()+score);
        if(updateData!=null){
            updateData.update();
        }
    }

    public void setMarble(int mb){
        state.setMb(mb);
        if(updateData!=null){
            updateData.update();
        }
    }

    public interface UpdateData{
        void update();
    }
}
