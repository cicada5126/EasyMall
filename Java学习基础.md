Java学习基础

最大的问题是java基础不过关。忘光光或者从来没有认真学过。

本文一阶段为回顾课堂内容，二阶段为对基础内容进行扩展。

[TOC]



------

## Java简介

解释性语言 ，源程序只编译一次 ，转换为Java字节码，Java虚拟机(JVM)对字节码进行解释和运行。

扩展：

> Java和C语言在解释和编译上的区别主要体现在以下几个方面：
>
> 1. **编译过程**:
>    - **C语言**: C语言是一种编译型语言。这意味着C语言的源代码在执行前需要通过编译器转换成机器码。编译过程通常分为预处理、编译和链接三个阶段。编译后的程序是可执行文件，可以直接在操作系统上运行。
>    - **Java**: Java是一种编译加解释型语言。Java源代码首先被编译成字节码（bytecode），这是一种中间形式的代码，不是直接的机器码。然后，字节码由Java虚拟机（JVM）解释执行。
> 2. **执行环境**:
>    - **C语言**: 编译后的C程序直接在操作系统上运行，不需要其他运行时环境。
>    - **Java**: Java程序需要在JVM上运行。JVM是一个可以执行Java字节码的虚拟计算机，它为Java程序提供了一个运行时环境。
> 3. **平台独立性**:
>    - **C语言**: C语言编译后的可执行文件是与平台相关的。在不同的操作系统或硬件上运行C程序，通常需要重新编译。
>    - **Java**: Java的“一次编写，到处运行”（Write Once, Run Anywhere, WORA）特性得益于其平台无关的字节码。Java字节码可以在任何安装了相应JVM的平台上运行，而不需要重新编译。
> 4. **内存管理**:
>    - **C语言**: C语言依赖程序员手动管理内存，包括分配和释放内存。
>    - **Java**: Java通过垃圾收集器自动管理内存，减少了内存泄漏和其他内存管理错误的风险。
> 5. **类型检查**:
>    - **C语言**: C语言在编译时进行类型检查，但类型转换更为灵活，有时可能导致类型安全问题。
>    - **Java**: Java在编译时进行严格的类型检查，并且不允许不安全的类型转换，增强了类型安全性。
> 6. **错误处理**:
>    - **C语言**: C语言通常使用整数错误码来指示函数调用的成功或失败。
>    - **Java**: Java使用异常处理机制来处理错误情况，这提供了一种更为结构化和灵活的错误处理方式。
> 7. **开发效率**:
>    - **C语言**: C语言通常需要更多的底层操作和手动内存管理，这可能会降低开发效率。
>    - **Java**: Java提供了丰富的标准库和自动内存管理，有助于提高开发效率。
>
> 总的来说，Java的设计目标是提供一种更安全、更易于移植和更易于维护的语言环境，而C语言则更接近硬件层面，提供了更高的性能和灵活性。



### java版本 

Java SE 

基础版

Java  EE

企业版 

Java  ME

嵌入式 

## 特性 

面向对象：最外层的数据类型是对象所有元素都通过类和对象来访问。封装、继承、多态

平台无关性

支持多线程

简单易学

安全性，多重安全防护机制，访问权限修饰符等

高效性

可靠性，异常处理和自动内存guan'li

编译解释并存

​                                                                                                                                                   



## 基本语法：

字符Character:Character.isDigit(Char)



Array

Integer

Map

Map<Character, Integer> frequencyMap = new HashMap<>();

Set

List<Map.Entry<Character, Integer>> list = new ArrayList<>(frequencyMap.entrySet());

### 基础篇：

#### 1.字符串

赋值

String(char[]  a)   String("abc")   String(char[] a , int offset,int length)

获取信息

str.length();

str.indexOf(substr)首次出现

str.lastindexOf(substr)最后一次出现

str.chartAt(int index)

字符串操作

str.substring(int beginindex)  str.substring(int beginindex,int endIndex)

str.trim()去掉空格

str.replace(char oldstr, char newstr)

str.startsWith(String prefix) str.endsWith(String s)

str.equals(String otherstr) str.equalsIgnoreCase(String s)

字典序 str.compareTo(String str2)str排序大于str2返回-1，相当于是不是小于参数的字典序

str.toLowerCase() toUpperCase()

str.split(String sign)  str.split(String sign,int limit)limit为模式匹配次数，最后一项为最后匹配后剩余部分

格式化str.format(String format,Object)时间、日期、常规类型

正则表达式

字符串生成器

StringBuilder类

append(content)

insert(int offset,args)

delete(int start,int end)

#### 2.数组

声明：

类型[] 数组名

类型 数组名[]

声明后要分配内存，即表明个数

初始化：

int arr[] =new int[]{1,2}

int arr2[]  = {1,2}

二维数组声明类似

数组基本操作：

二维数组使用foreach遍历

```
int arr[][]={{1,2},{3,4}};
int i=0;
for(int a[] : arr)
{
	i++;//记录一维
	int j = 0 ;//记录二维
	for(int num : a)
	{
		j++;
	}

}
```

fill(int[] a,int value) 如Arrays.fill(arr,0)对数组arr使用0初始化。

fill(int[] a,  int fromindex(包括),int toindex(不包括),int value)

Arrays.sort()String类型使用字典序

copyOf(arr,int newlength)

查找数组 Arrays.binarySearch(Object a[] , value)二分查找

#### 3.包装类

Integer  num = new Integer(7);

Integer  num = new Integer("45");

Integer.toString()  Integer.toBinaryString() Integer.toHexString ..

Boolean Byte

Character

isUpperCase isLowerCase  toString toLowerCase toUpperCase compareTo equals

Double Number

Math



### 核心篇：

1. 



1. #### 集合类

   Collection Set和List类继承Collection类 add(E e) remove() isEmpty() size() iterator()

   ```
   Iterator<string> it = list.iterator();
   while(it.hasNext())
   {
   
   }
   ```

   List

   get(int index)获取指定索引元素

   set(int index,Object obj)修改为指定对象

   ArrayList

   LinkList

   Set 不排序，不包含重复对象

   HashSet

   TreeSet

   first() last() comparator() headSet()

   Deque<Character> stack = new LinkedList<Character>();

   

   

   Map

   .getOrDefauld

   不包含相同key，一个key对应一个value

   put(key ,value)

   containsKey

   get(key)

   keySet()

   HashMap高效查找

   TreeMap键可以保留一定关系，基于红黑树

   ```
   Map<String,String> map = new HashMap<>();
   //..定义键值对
   map.put(key,value)
   Set<String> set = map.keySet();
   Iterator<String> it = set.iterator()
   while(it.hasNext)
   {
   	String str=it.next();
   }
   TreeMap<String, String> tp=new TreeMap<>();
   tp.putAll(map);
   //此时tp里Map集合根据键升序
   ```

   

2. #### 反射和Annotation



1. #### 枚举与泛型

2. #### 多线程

3. #### 数据库操作

