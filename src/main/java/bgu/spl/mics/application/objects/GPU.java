package bgu.spl.mics.application.objects;

import java.util.Collection;

/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU {
    /**
     * @INV:
     *      0<=getCurrVRAMSize()<=getVRAMLimitation()
     *      getBatchesAmountToProcess()>=0
     *      getCurrTick()>=0
     *      getStartTick()>=-1
     *      getProcessTick()==1 iff getType()==RTX3090
     *      getProcessTick()==2 iff getType()==RTX2080
     *      getProcessTick()==4 iff getType()==GTX1080
     */

    /**
     * Enum representing the type of the GPU.
     */
    enum Type {RTX3090, RTX2080, GTX1080}

    private Type type;
    private Model model; // The model the gpu currently working on (null for none) *****
    private Cluster cluster;

    //WE ADDED *****
    private int currVRAMSize; //number of batches that processed by cpu, and waiting to be processed by gpu
    private int VRAMLimitation; //3090=32 processed batches, 2080=16 processed batches, 1080=8 processed batches
    private int processTick; //3090=1 tick, 2080=2 ticks, 1080=4 ticks (process after the cpu returned)
    private Collection<DataBatch> dataBatches; //data batches on the disk
    private int batchesAmountToProcess; //initialized to be model.data.size/1000,
                                        // we will decrease until getting to 0, and then the gpu is done processing
    private int currTick; //might be changed later (only for testing purpose)
    private int startTick;

    public GPU (Type type) {
        //Decided for now that this is the only constructor
    }

    /**
     * @PRE:
     * 	 	none
     * @POST:
     * 	 	none
     * (basic query)
     */
    public int getCurrVRAMSize(){
        return currVRAMSize;
    }

    /**
     * @PRE:
     * 	 	none
     * @POST:
     * 	 	none
     * (basic query)
     */
    public int getVRAMLimitation() {
        return VRAMLimitation;
    }

    /**
     * @PRE:
     * 	 	none
     * @POST:
     * 	 	none
     * (basic query)
     */
    public int getCurrTick() { return currTick;}

    /**
     * @PRE:
     * 	 	none
     * @POST:
     * 	 	none
     * (basic query)
     */
    public int getStartTick() { return startTick;}

    /**
     * @PRE:
     * 	 	none
     * @POST:
     * 	 	none
     * (basic query)
     */
    public int getBatchesAmountToProcess() { return batchesAmountToProcess;}

    /**
     * @PRE:
     * 	 	none
     * @POST:
     * 	 	none
     * (basic query)
     */
    public Model getModel() {return model;}

    /**
     * @PRE:
     * 	 	none
     * @POST:
     * 	 	none
     * (basic query)
     */
    public int getProcessTick() {return processTick;}

    /**
     * @PRE:
     * 	 	none
     * @POST:
     * 	 	none
     * (basic query)
     */
    public Collection<DataBatch> getDataBatches(){
        return dataBatches;
    }

    /**
     * @PRE:
     *      model==null
     * @POST:
     *      model!=null
     */
    public void setModel(Model model) {}

    /**
     * @PRE:
     *      dataBatches.size()==0
     *      batchesAmountToProcess==0
     * @POST:
     *      dataBatches.size()==(model.getData().getSize())/1000
     *      batchesAmountToProcess == (model.getData().getSize())/1000
     */
    //takes the model.data.getSize(), split by 1000 and create this amount of batches,
    //each batch get the start_index (0,1000,2000,...) and pushed to Collection<DataBatch>
    public void splitToBatches() {}


    /**
     * @PRE:
     *      dataBatches.size()>0
     * @POST:
     *      dataBatches.size()==@PRE(dataBatches.size())-1
     */
    //(probably GPUService will check dataBatches.size()>0 and then activate this function)
    //send unprocessed batch to the cluster, cluster gives it to one of the cpu
    public void sendBatchToCluster(){}

    /**
     * @PRE:
     *      batchesAmountToProcess > 0
     *      currVRAMSize < VRAMLimitation
     *
     * @POST:
     *      currVRAMSize = @PRE(currVRAMSize)+1
     */
    //increase to currVRAMSize by 1 for each added batch, here we throw away the batches
    //take batch from cluster only if currVRAMSize < VRAMLimitation
    public void getProcessedBatchFromCluster() {}

    /**
     * @PRE:
     *      none
     * @POST:
     *      if (@PRE(currVRAMSize)!=0 && currTick = startTick + processTick) :
     *              currVRAMSize = @PRE(currVRAMSize)-1 &&
     *              batchesAmountToProcess == @PRE(batchesAmountToProcess) - 1
     *      currTick = @PRE(currTick) + 1
     */
    public void updateTick() {
        /*
        currTick++
        if(currVRAMSize > 0){
           if(currTick-start_tick > processTick){
             currVRAMSize--
             batchesAmountToProcess--
            }
         }
         */
    }

    /**
     * @PRE:
     *      model.getStatus()==Trained
     *      model.getResult()==none
     * @POST:
     *      model.getStatus()==Tested
     *      model.getResult()!=none
     */
    public void testProcess(){}

    /**
     * @PRE:
     *      none
     * @POST:
     *      model==null
     *      currVRAMSize==0
     *      dataBatches.size()==0
     *      batchesAmountToProcess==0
     *      startTick==-1
     */
    public void resetGPU() {
        //model=null, currVRAMSize=0, dataBatches.clear(), batchesAmountToProcess=0, startTick=-1
    }
}