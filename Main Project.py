#Modules

from tkinter import*
import mysql.connector
import random

#DBase Connection

sqluser=input("Enter your SQL database username:")
sqlpass=input("Enter your SQL database password:")
conn=mysql.connector.connect( host='localhost', user=sqluser , passwd=sqlpass , database='bank' )
cur=conn.cursor()
cur.execute("create database if not exists bank")
cur.execute("use bank")
cur.execute("create table if not exists userdata(username varchar(30),password varchar(30),name varchar(30),\
            actno varchar(5),contactno varchar(10),age int,city varchar(10),balance int default '5000')")

#Functions

def login():
    global uname
    global psswd
    
    uname=e.get()
    psswd=e1.get()
    
    cur.execute("select username from userdata")
    user1=cur.fetchall()
    conn.commit()
    user2=[]
    for i in range(len(user1)):
        user2.append(user1[i][0])
        
    cur.execute("select password from userdata")
    pwd1=cur.fetchall()
    conn.commit()
    pwd2=[]
    for i in range(len(pwd1)):
        pwd2.append(pwd1[i][0])

    if (uname in user2) and (psswd in pwd2):
        print("Logged in successfully!!")
        window.destroy()
        main()
    else:
        print("Login unsuccessful!!")
        print("Password and Username do not match!!")
        window.destroy()
        ch=input("Do you want to try again?(y/n):")
        if ch.lower()=='y':
            tkinter()
        else:
            quit()

def clear_text():
   e.delete(0, END)
   e1.delete(0,END)
   
        
def signup():
    global sgn
    window.destroy()
    
    sgn = Tk()
    sgn.geometry("500x500")
            
    sg = Label(sgn, text="Please Fill up the details",font=("callibri",18,"bold"))
    sg.pack()

    sg1 = Label(sgn, text="Username",font=("arial",14))
    sg1.pack()

    global g
    g = Entry(sgn)
    g.pack()

    sg2 = Label(sgn, text="Password",font=("arial",14))
    sg2.pack()

    global g1
    g1 = Entry(sgn)
    g1.pack()

    sg3 = Label(sgn, text="Full Name",font=("arial",14))
    sg3.pack()

    global g3
    g3 = Entry(sgn)
    g3.pack()

    sg4 = Label(sgn, text="Age",font=("arial",14))
    sg4.pack()

    global g4
    g4 = Entry(sgn)
    g4.pack()

    sg5 = Label(sgn, text="Contact No.",font=("arial",14))
    sg5.pack()

    global g5
    g5 = Entry(sgn)
    g5.pack()

    sg6 = Label(sgn, text="City",font=("arial",14))
    sg6.pack()

    
    global g6
    g6 = Entry(sgn)
    g6.pack()

    
    s = Label(sgn, text='')
    s.pack()

    f= Button(sgn,text="Submit",font=("arial",15),command=append)
    f.pack()

    
def append():
    un=g.get()
    upwd=g1.get()
    fname=g3.get()
    ag=g4.get()
    cntctno=g5.get()
    city=g6.get()
    acno=random.randint(10000,99999)
    bala='5000'
    data=(un,upwd,fname,acno,cntctno,ag,city,bala)
    sql1='insert into userdata values(%s,%s,%s,%s,%s,%s,%s,%s)'
    cur.execute(sql1,data)
    conn.commit()
    sgn.destroy()
    tkinter()
    print("Account Created successfully!!")

def main():
    print("\t\t\t\t*********************")
    print("\t\t\t\tONLINE BANKING SYSTEM")
    print("\t\t\t\t*********************")
    print("\t\t\t\t\t\t\t\t\t\t\t\tYou are logged in as: ",uname)
    print("MAIN MENU")
    print("1. DEPOSIT AMOUNT")
    print("2. BALANCE ENQUIRY")
    print("3. TRANSFER AMOUNT")
    print("4. CHANGE PASSWORD")
    print("5. DELETE ACCOUNT")
    print("6. ACCOUNT DETAILS")
    print("7. EXIT\n")
    ch = input(("Enter your choice :"))
    if ch =='1':
        deposit()
    elif ch == '2':
        balance()
    elif ch == '3':
        transferacc()
    elif ch == '4':
        chgpsswd()
    elif ch == '5':
        delacc()
    elif ch == '6':
        details()
    elif ch == '7':
        quit()
    else:
        print("Please enter valid choice!")
        main()
        
    

def deposit():
    print("\t\t\t\t**************")
    print("\t\t\t\tAMOUNT DEPOSIT")
    print("\t\t\t\t**************")
    var1=input("Enter the amount to be deposited:")
    cur.execute("update userdata set balance=balance+%s where username=%s",(var1,uname))
    conn.commit()
    print("Amount has been deposited successfully!!\n")
    main()
    

def balance():
    print("\t\t\t\t***************")
    print("\t\t\t\tBALANCE ENQUIRY")
    print("\t\t\t\t***************")
    uname1=list(uname)
    cur.execute("select balance from userdata where username='"+uname+"'")    
    amt=cur.fetchall()
    balamt=[]
    for i in amt:
        balamt.extend(i)
    print("Balance :",balamt[0])
    main()
    
        
def transferacc():
    print("\t\t\t\t***************")
    print("\t\t\t\tAMOUNT TRANSFER")
    print("\t\t\t\t***************")
    var2=int(input("Enter the account number of person: "))
    var3=int(input("Enter the amount to be transferred: "))
    cur.execute("select actno from userdata")
    res=cur.fetchall()
    z=str(var2)
    my_result = False
    for i in res :
      if (z,) == i:
         my_result = True
         break
    if (my_result == True):
        cur.execute("update userdata set balance=balance-%s where username=%s",(var3,uname))
        conn.commit()
        cur.execute("update userdata set balance=balance+%s where actno=%s",(var3,var2))
        conn.commit()
        print("Amount has been transferred successfully!!\n")
        ch=input("Do you want to make another transaction?(y/n):")
        if ch.lower()=='y':
            transferacc()
        else:
            main()
    else:
        print("Specified account doesn't exist!\n")
        print("Transaction cancelled!")
        main()


def chgpsswd():
    print("\t\t\t\t***************")
    print("\t\t\t\tCHANGE PASSWORD")
    print("\t\t\t\t***************")
    password=str(psswd)
    i=0
    while True:
        x=input("Enter your existing password:")
        if x==password:
            y=input("Enter your new password:")
            z=input("Re-enter your new password:")
            if y==z:
                cur.execute("update userdata set password=%s where username=%s",(y,uname))
                conn.commit()
                print("Password Changed successfully!\n")
                main()
            else:
                print("Password's don't match\n")
                continue
        else:
            print("Password is incorrect!\n")
            i=i+1
            if i<=2:
                print("You only have ",(3-i), "attempts left!")
            else:
                print("Please retry later!\n")
                quit()
            
    
    

def delacc():
    print("\t\t\t\t**************")
    print("\t\t\t\tDELETE ACCOUNT")
    print("\t\t\t\t**************")
    while True:
        x=input("Are you sure you want to delete your account?(y/n):")
        if x.lower()=='y':
            y=input("Enter your password :")
            if y==psswd:
                uname1=list(uname)
                cur.execute("delete from userdata where username='"+uname+"'")
                conn.commit()
                print("Your account has been successfully deleted!\n")
                quit()
            else:
                print("Password incorrect!\n")
                delacc()
        else:
            main()

def details():
    print("\t\t\t\t***************")
    print("\t\t\t\tAMOUNT TRANSFER")
    print("\t\t\t\t***************\n")
    cmd = ("select * from userdata where username='"+uname+"'")
    cur.execute(cmd)
    r1 = cur.fetchall()
    print("=================================================================================================================")
    print("| USERNAME       PASSWORD       NAME         ACC NUMBER     CONTACT NUMBER      AGE         CITY         BALANCE|")
    print("=================================================================================================================")
    for i in range(len(r1)):
        print("| ",end="")
        print(str(r1[i][0]).ljust(15," "),end="")
        print(r1[i][1].ljust(16," "),end="")
        print(r1[i][2].ljust(18," "),end="")
        print(str(r1[i][3]).ljust(14," "),end="")
        print(r1[i][4].ljust(16," "),end="")
        print(str(r1[i][5]).ljust(11," "),end="")
        print(str(r1[i][6]).ljust(13," "),end="")
        print(str(r1[i][7]).ljust(5," "),end="|")
        print()
        main()

def tkinter():
    global e
    global e1
    global window
    
    window = Tk()
    window.geometry("500x500")
    window.title("Online Banking System")

    w = Label(window, text="",font=("calibri",20,"bold"))
    w.pack()

    w1 = Label(window, text="Username",font=("arial",14))
    w1.pack()
   
    e = Entry(window)
    e.pack()

    w2 = Label(window, text="Password",font=("arial",14))
    w2.pack()

    e1 = Entry(window)
    e1.pack()

    msg = Label(window, text="(Note:Passwords are case-sensitive!)",font=("arial",10))
    msg.pack()

    empt = Label(window, text="")
    empt.pack()

    clr=Button(window,text="Clear", command=clear_text, font=('arial',10))
    clr.pack()

    empt1 = Label(window, text="")
    empt1.pack()
 
    b=Button(window,text="Submit",font=("arial bold",15),command=login)
    b.pack()

    empt1 = Label(window, text="")
    empt1.pack()

    s = Button(window,text="New User? Sign Up!",font=("arial",15),command=signup)
    s.pack()

    msg2 = Label(window, text="(Minimum balance - 5000)",font=("arial",12))
    msg2.pack()

    empt1 = Label(window, text="")
    empt1.pack()


tkinter()
