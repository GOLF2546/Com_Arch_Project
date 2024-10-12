import java.util.*;

public class MachineCodeSimulator {
    private int[] memory; //memory เอาไว้เก็บ instruction
    private Map<String, Integer> registers; //เก็บ instruction
    private int pc; //เก็บค่าบรรทัดในปัจจุบัน
    private static final int NUMMEMORY = 65536; //instruction สูงสุด 65536
    private static final int NUMREGS = 8; //number of registers
    private int total_instruction=0;// count number of instruction
    
    public MachineCodeSimulator() {     //initialize registers ทุกตัวและ set program counter เป็น 0 
        
        memory = new int[NUMMEMORY]; //memory เอาไว้เก็บ instruction 
        registers = new HashMap<>(); 
        registers.put("R0", 0); //value 0
        registers.put("R1", 0); //n input to function
        registers.put("R2", 0); //r input to function
        registers.put("R3", 0); //return value of function
        registers.put("R4", 0); //local variable for function
        registers.put("R5", 0); //stack pointer
        registers.put("R6", 0); //temporary value (can hold different values at different times, e.g.+1, -1, function address)
        registers.put("R7", 0); //return address
        pc = 0;
    }

    public static void runSimulate(List<String> input) {  //simulate machine code from part 1 (input is binary)
        BinaryStringToDecimal(input); //convert binary to decimal
        MachineCodeSimulator sim = new MachineCodeSimulator(); //initailize memmory and register
        sim.loadProgram(input);//put machine code decimal in memory[]
        sim.run();//simulate machine code
        printState(sim); //print final state
    }

    //machinecode เก็บใน memory[]
    public void loadProgram(List<String> machineCode) {//put machine code decimal in memory[]
        for (int i = 0; i < machineCode.size(); i++) {//Loop to put all machine code
            memory[i] = Integer.parseInt(machineCode.get(i));//convert string to int before put it in
           }
    }

    public void run() { 
        while (true) {
           String instruction = fetch();//get instuction from pc (in binary) and pc++
           if (!decodeExecute(instruction)){//decode instruction
               break;// if halt
           }
        }
        //conclusion
       System.out.println("total of "+ total_instruction +" instructions executed");
       System.out.println("final state of machine:");
   }
    
    private String fetch() {//get instruction in binary and increase pc
        int instruction = memory[pc];
        total_instruction++; //count instuction
        pc++; //next instruction

        //convert decimal to binary 25 bits
        return String.format("%25s", Integer.toBinaryString(instruction)).replace(' ', '0');
    }

    private boolean decodeExecute(String instruction) {
        String opcode = instruction.substring(0, 3); //Bits 24-22 opcode 
        String rs;
        String rd;
        String rt;
        int regBValue;
        int regAValue;
        switch (opcode) {//check opcode
            case "000": // ADD
                rd = "R" + Integer.parseInt(instruction.substring(22, 25), 2); //Bits 2-0 destReg
                rs = "R" + Integer.parseInt(instruction.substring(3, 6), 2); //Bits 21-19 regA
                rt = "R" + Integer.parseInt(instruction.substring(6,9), 2); //Bits 28-16 regB
                regAValue = registers.getOrDefault(rs, 0); //get value from regA
                regBValue = registers.getOrDefault(rt, 0); //get value from regB
                registers.put(rd, regAValue + regBValue);
            break;
            case "001": // nand (Nand ค่าใน regA ด้วยค่าใน regB และเอาค่าไปเก็บใน destReg)
                rs = "R" + Integer.parseInt(instruction.substring(3, 6), 2); // Bits 21-19 reg A (rs)
                rt = "R" + Integer.parseInt(instruction.substring(6,9), 2); //  Bits 18-16 res B (rt)
                rd = "R" + Integer.parseInt(instruction.substring(22, 25), 2); //Bits 2-0  destReg (rd)
                regAValue = registers.getOrDefault(rs , 0); //get value from regA
                regBValue = registers.getOrDefault(rt, 0); //get value from regB
                registers.put(rd,~(regAValue & regBValue) & 0xFFFFFFFF); //AND , NOT value in regA by value in regB and put in it destReg
                break;
            case "010": // LOAD (Load regB จาก memory และ memory address หาได้จากการเอา offsetField บวกกับค่าใน regA)
                rt = "R" + Integer.parseInt(instruction.substring(6, 9), 2);//bits 18-16 regB (rt)
                rs = "R" + Integer.parseInt(instruction.substring(3, 6), 2);//bits 21-19 regA (rs)
                regAValue = Integer.valueOf(registers.get(rs));//get value from regA
                int address = regAValue + Integer.parseInt(instruction.substring(9,25), 2); //offsetField บวกกับค่าใน regA
                registers.put(rt, memory[address]);//load the value from the memory to regB
                if(rt .equals("R0"))  registers.put(rt, 0);//R0 need to be only 0
                break;
            case "011": // STORE (Store regB ใน memory และ memory address หาได้จากการเอา offsetField บวกกับค่าใน regA)
                rt = "R" + Integer.parseInt(instruction.substring(6, 9), 2);//r1  Bits 18-16 reg B (rt) 
                rs  = "R" + Integer.parseInt(instruction.substring(3, 6), 2);//r0  Bits 21-19 reg A (rs)
                regAValue = Integer.valueOf(registers.get(rs)); //get value from regA
                regBValue = Integer.valueOf(registers.get(rt)); //get value from regB
                int storeaddress = regAValue + Integer.parseInt(instruction.substring(9,25), 2); //offsetField บวกกับค่าใน regA
                memory[storeaddress]=regBValue; //store regB in memory
                break;
            case "100"://beq (ถ้า ค่าใน regA เท่ากับค่าใน regB ให้กระโดดไปที่ address PC+1+offsetField ซึ่ง PC คือ address ของ beq instruction)
                
                rt = "R" + Integer.parseInt(instruction.substring(6, 9), 2);//Bits 18-16 reg B (rt)
                rs = "R" + Integer.parseInt(instruction.substring(3, 6), 2);//Bits 21-19 reg A (rs)
                regBValue = Integer.valueOf(registers.get(rt));//get value from reg B
                regAValue = Integer.valueOf(registers.get(rs));//get value from reg A
                int offset=convertNum(Integer.parseInt(instruction.substring(9,25), 2)); //offsetField บวกกับค่าใน regA
                if(regAValue==regBValue) pc=pc+offset; //if regA = regB we will jumb to this (pc+1+offsetField)
                break;
            case "101": //jalr (เก็บค่า PC+1 ไว้ใน regB ซึ่ง PC คือ address ของ jalr instruction และกระโดดไปที่ address ที่ถูกเก็บไว้ใน regA แต่ถ้า regA และ regB คือ register ตัวเดียวกัน ให้เก็บ PC+1 ก่อน และค่อยกระโดดไปที่ PC+1)
                rs = "R" + Integer.parseInt(instruction.substring(3, 6), 2); //Bits 21-19 reg A (rs)
                rd = "R" + Integer.parseInt(instruction.substring(6, 9), 2); //Bits 18-16 reg B (rd)
                regAValue = Integer.valueOf(registers.get(rs)); //get value from reg A
                registers.put(rd, pc); //เก็บค่า PC+1 ไว้ใน regB (fetch alredy +1)
                pc=regAValue; //jumb to address in regA
                break;
            case "110"://halt (เพิ่มค่า PC เหมือน instructions อื่นๆ และ halt เครื่อง นั่นคือให้ simulator รู้ว่าเครื่องมีการ halted เกิดขึ้น)
                System.out.println("machine halted");
                return false;
            case "111"://noop (ไม่ทำอะไรเลย)
                break;
        }
        return true;
    }

    private static void printState(MachineCodeSimulator state) {//print memory and register
        System.out.println("\n@@@\nstate:");
        System.out.printf("\tpc %d\n", state.pc);
        System.out.println("\tmemory:");
        for (int i = 0; i < NUMMEMORY; i++) {//print memory 
            if(state.memory[i]==0 && state.memory[i+1] == 0){ //if memory is 0 and 0 we will stop (assume that's it's finished)
                break;
            }
            System.out.printf("\t\tmem[ %d ] %d\n", i, state.memory[i]);
            
        }
        System.out.println("\tregisters:");
        for (int i = 0; i < NUMREGS; i++) {//print register
            System.out.printf("\t\treg[ %d ] %d\n", i, state.registers.get("R"+i));
        }
        System.out.println("end state"); 
    }
    public int convertNum(int num) {//convert 16 bits to 32 bits (signed)
        if ((num & (1 << 15)) != 0) {// ถ้าบิตที่ 16 (bit ที่ตำแหน่ง 15) เป็น 1 (เลขลบใน signed 16-bit)
            num -= (1 << 16); // หักค่า 2^16 เพื่อแปลงเป็นค่าลบที่ถูกต้อง
        }
        return num;
    }

    public static void BinaryStringToDecimal(List<String> inputArray) { //loop to convert binary to decimal for List<String>
            for(int i = 0; i < inputArray.size(); i++){
                inputArray.set(i, binaryToDecimal(inputArray.get(i)));
            }    
    }

    public static String binaryToDecimal(String binaryStr)//convert binary 32 bits to decimal
    {
            if (binaryStr.length() != 32) {//id not 32 bits
                throw new IllegalArgumentException("Binary string must be exactly 32 bits long");
            }
            if (binaryStr.charAt(0) == '1') {//for negative numbers
                long unsignedValue = Long.parseUnsignedLong(binaryStr, 2);
                int signedValue = (int)(unsignedValue & 0xFFFFFFFF);//make it negative
                return Integer.toString(signedValue);
            } else {
                // For positive numbers, directly convert to hexadecimal
                long decimalValue = Long.parseLong(binaryStr, 2);
                return Long.toString(decimalValue);
            }
       
    }
    

}