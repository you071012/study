策略模式说明
策略模式代表了解决一类算法的通用解决方案，你可以在运行时选择使用哪种方案。
eg：数字匹配算法基类 ValidationStrategy 包含多种实现
    实现一：匹配输入字符是否是英文字母 --> LetterMatchCase
    实现二：匹配输入字符是否是数字 --> NumberMatchCase
    
策略验证类 --> ParamValidator提供策略执行方法 validator(String param),通过指定的策略实现，执行当前策略
策略测试类 --> StrategyTest